/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object designed to hold a reference to a fully resolvable property (with
 * name, and context), but that cannot be dereferenced immediately because
 * the object may still be constructed.
 * 
 * Accessing the property after the object is fully constructed is quick
 * (there is no synchronization required), and the resolution is cached so 
 * that further accesses are immediate.
 * 
 * @param <T> The actual property implementation (if needed)
 * @author Elscouta
 */
public class Ref<T extends IObject> 
{
    private T prop;
    private final String path;
    private final Cast<T> cast;
    private final IObjectStore context;
    
    private Ref(String path, IObjectStore context, Cast<T> cast)
    {
        this.prop = null;
        this.path = path;
        this.context = context;
        this.cast = cast;
    }
    
    public T get() 
            throws IObject.Mistyped, IObject.Unknown, Ref.Invalid
    {
        if (prop == null)
            resolve();
        
        return prop;
    }
    
    private final static Pattern REGEX_DIRECT = 
            Pattern.compile("^([a-z_]+)$");
    private final static Pattern REGEX_INDIRECT = 
            Pattern.compile("^([a-z_]+)[.]([a-z_.]+)$");
    private final static Pattern REGEX_ARRAY =
            Pattern.compile("^([a-z_.]+)\\[([a-z_.]+)\\]$");

    private static IObject resolvePart(IObjectStore context, String path)
            throws IObject.Mistyped, IObject.Unknown, Ref.Invalid
    {
        try 
        {
            Matcher m1 = REGEX_DIRECT.matcher(path);
            if (m1.matches())
            {
                return context.get(path);
            }
        
            Matcher m2 = REGEX_INDIRECT.matcher(path);
            if (m2.matches())
            {
                IObjectStore subcontext = resolvePart(context, m2.group(1)).asObject();
                return resolvePart(subcontext, m2.group(2));
            }
            
            Matcher m3 = REGEX_ARRAY.matcher(path);
            if (m3.matches())
            {
                IObjectStore subcontext = resolvePart(context, m3.group(1)).asMap();
                return resolvePart(subcontext, m3.group(2));
            }
        }
        catch (Invalid e)
        {
            throw new Invalid(e, path);
        }
        
        throw new Invalid(path);        
    }
    
    public void resolve() 
            throws IObject.Mistyped, IObject.Unknown, Ref.Invalid
    {
        prop = cast.apply(resolvePart(context, path));
    }
    
    @FunctionalInterface
    private static interface Cast<T extends IObject>
    {
        T apply(IObject p) throws IObject.Mistyped;
    }
    
    public static class Int extends Ref<IObjectInt>
    {
        public Int(String path, IObjectStore context)
        {
            super(path, context, p -> p.asInt());
        }
    }
    
    public static class Str extends Ref<IObjectString>
    {
        public Str(String path, IObjectStore context)
        {
            super(path, context, p -> p.asString());
        }
    }
    
    public static class Obj extends Ref<IObjectStore>
    {
        public Obj(String path, IObjectStore context)
        {
            super(path, context, p -> p.asObject());
        }
    }
    
    public static class Any extends Ref<IObject>
    {
        public Any(String path, IObjectStore context)
        {
            super(path, context, p -> p);
        }
    }

    public static class Invalid extends XMLError
    {
        String partString;
        String fullString;
        
        public Invalid(String str)
        {
            super();
            partString = str;
            fullString = str;
        }
        
        public Invalid(Invalid e, String str)
        {
            super(e);
            partString = e.partString;
            fullString = str;
        }
        
        @Override public String toString()
        {
            return String.format("Invalid property: \n" +
                                 "Complete string: %s" +
                                 "Invalid subexpression: %s",
                                 fullString, partString);
        }
    }
}
