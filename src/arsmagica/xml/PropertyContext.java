/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * A 
 * 
 * @author Elscouta
 */
public class PropertyContext implements Context
{
    private final Map<String, IObject> properties;

    protected PropertyContext()
    {
        properties = new HashMap<>();
    }
    
    public static PropertyContext createWrapper(String key, IObject value)
    {
        PropertyContext c = create();
        c.addProperty(key, value);
        return c;
    }
    
    public static PropertyContext create() 
    {
        return new PropertyContext();
        
    }

    public void addProperty(String key, IObject obj)
    {
        if (properties.containsKey(key))
            throw new RuntimeException("Attempting to insert duplicate property");
        
        properties.put(key, obj);
    }
    
    @Override
    public final IObject get(String key)
            throws Context.Unknown
    {
        if (!properties.containsKey(key))
            throw new Context.Unknown(key, this);
        
        return properties.get(key);
    }
}
