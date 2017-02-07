/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.EntityDesc;
import arsmagica.xml.XMLError;
import java.util.HashMap;
import java.util.Map;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;


/**
 *
 * @author Elscouta
 */
public class Entity implements IObjectStore
{
    @Override public IObjectStore getContext() { return parent; }
    @Override public String getType() { return type; }
    @Override public IObjectStore asObject() { return this; }
    
    private final Map<String, IObject> properties;
    private final Entity parent;
    private final String type;
    
    public Entity(EntityMgr eMgr, Entity parent, EntityDesc desc)
            throws XMLError
    {
        this.parent = parent;
        this.properties = new HashMap<>();
        this.properties.put(parent.getType(), parent);
        desc.initProperties(eMgr, this, properties);
        this.type = desc.getType();
    }
    
    @Override
    public IObject get(String key)
            throws IObject.Unknown
    {
        if (!properties.containsKey(key))
            throw new IObject.Unknown(key, this);
        
        return properties.get(key);
    }
}
