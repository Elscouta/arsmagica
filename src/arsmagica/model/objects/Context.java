/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model.objects;


/**
 *
 * @author Elscouta
 */
public interface Context 
{
    public IObject get(String key) throws Context.Unknown;
    
    public static Context createWrapper(String key, IObject value, 
            Context def)
    {
        return new Context() {
            @Override 
            public IObject get(String k) 
                    throws Unknown
            {
                if (key.equals(k))
                    return value;
                else
                    return def.get(k);
            }
        };
    }
    
    public static Context create()
    {
        return new Context() {
            @Override
            public IObject get(String k)
                    throws Unknown
            {
                throw new Unknown("Unknown key", this);
            }
        };
    }
    
    /**
     * Thrown when a property is used, but such a property doesn't exist.
     */
    public static class Unknown extends Exception
    {
        public Unknown(String tried, Context store)
        {
            super(String.format("Unknown property in %s: %s", store.toString(), tried));
        }
    }

}
