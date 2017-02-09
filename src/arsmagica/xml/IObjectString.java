/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * Represents a string property.
 * Type of strings is "string".
 * 
 * Strings are usually immutable once created. They are generally defined
 * through a names file, but can also be explicitly defined through the
 * 'property' tag.
 * 
 * @author Elscouta
 */
public final class IObjectString implements IObject
{
    @Override public Context getContext() { return context; }
    @Override public String getType() { return "string"; }
    @Override public IObjectString asString() { return this; }

    private final Context context;
    private final Expression<String> str; 
    
    public IObjectString(Context context, Expression<String> str)
    {
        this.context = context;
        this.str = str;
    }
    
    @Override
    public String toString()
    {
        try {
            return str.resolve(context);
        } catch (Ref.Error e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Expression<String> toStringTemplate()
    {
        return str;
    }
}
