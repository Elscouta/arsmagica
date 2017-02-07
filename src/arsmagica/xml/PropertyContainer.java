/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.PropertyDesc;
import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.model.World;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Elscouta
 */
public abstract class PropertyContainer implements IObjectStore
{
    @Override public IObjectStore getContext() { return this; }
    @Override public IObjectStore asObject() { return this; }

    private final Map<String, IObject> properties;
    
    public PropertyContainer(World w, List<PropertyDesc> propertyDescs)
            throws XMLError
    {
        properties = new HashMap<>();
        for (PropertyDesc p : propertyDescs)
            properties.put(p.getID(), p.create(w, this));       
    }
    
    protected final void addProperty(String key, IObject obj)
    {
        properties.put(key, obj);
    }
    
    @Override
    public final IObject get(String key)
            throws IObject.Unknown
    {
        if (!properties.containsKey(key))
            throw new IObject.Unknown(key, this);
        
        return properties.get(key);
    }
}
