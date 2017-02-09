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
            throws Error
    {
        if (prop == null)
            resolve();
        
        return prop;
    }
    
    private final static Pattern REGEX_DIRECT = 
            Pattern.compile("^([A-Za-z_][A-Za-z_0-9]*)$");
    private final static Pattern REGEX_INDIRECT = 
            Pattern.compile("^([A-Za-z_][A-Za-z_0-9]*)[.]([A-Za-z_][A-Za-z_0-9]*)$");
    private final static Pattern REGEX_ARRAY =
            Pattern.compile("^([A-Za-z_][A-Za-z_0-9.]*)\\[([A-Za-z_][A-Za-z_0-9.]*)\\]$");

    private static IObject resolvePart(IObjectStore context, String path)
            throws MisformedError, IObject.Mistyped, IObject.Unknown
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
        
        throw new MisformedError(path);        
    }
    
    public void resolve() 
            throws Error
    {
        try {
            prop = cast.apply(resolvePart(context, path));
        } catch (MisformedError e) {
            throw new Error("Misformed expression:", e);
        } catch (IObject.Mistyped e) {
            throw new Error("Wrong type of object when resolving.", e);
        } catch (IObject.Unknown e) {
            throw new Error("Unknown property when resolving.", e);
        }
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
    
    public static class Obj extends Ref<PropertyContainer>
    {
        public Obj(String path, IObjectStore context)
        {
            super(path, context, p -> p.asObject());
        }
    }
    
    public static class List extends Ref<IObjectList>
    {
        public List(String path, IObjectStore context)
        {
            super(path, context, p -> p.asList());
        }
    }
    
    public static class Any extends Ref<IObject>
    {
        public Any(String path, IObjectStore context)
        {
            super(path, context, p -> p);
        }
    }

    public static class MisformedError extends Error
    {
        public MisformedError(String str)
        {
            super(String.format("Invalid subexpression: %s", str));
        }
    }
    
    public static class Error extends Exception
    {
        public Error(String str) { super(str); }
        public Error(String str, Exception e) { super(str, e); }                
    }
}
