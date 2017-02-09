/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.World;
import java.util.HashMap;
import java.util.Map;

/**
 * An helper class for any object that wish to own a static set of 
 * properties. Provides support for destruction notification.
 * 
 * @author Elscouta
 */
public abstract class PropertyContainer implements IObjectStore
{
    @Override public IObjectStore getContext() { return this; }
    @Override public PropertyContainer asObject() { return this; }

    private final Map<String, IObject> properties;
    private IObjectList owners;
    
    public PropertyContainer(World w)
    {
        properties = new HashMap<>();
        owners = new IObjectList(this);
    }
    
    public final void addProperty(String key, IObject obj)
            throws XMLError
    {
        if (properties.containsKey(key))
            throw new XMLError("Attempting to insert duplicate property");
        
        obj.registerOwner(this);
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

    /**
     * Destroys the container and notifies owners of the destruction.
     * This function is re-entrant.
     * 
     * Note that during a destruction process, object states may be unstable
     * which means only unregistration should be performed.
     */
    public void destroy()
    {
        IObjectList listeners = owners.removeAll();
        owners = null;
        
        try {
            for (IObject l : listeners)
                ((IObjectOwner) l).notifyMemberDestroyed(this);
        } catch (Mistyped e) {
            throw new RuntimeException("Non-owner in owner list", e);
        }
    }
    
    /**
     * FIXME: Must destroy the entitiy
     * @param object The object that got destroyed
     */
    @Override
    public void notifyMemberDestroyed(IObject object)
    {
        if (owners != null)
            destroy();
    }
    
    @Override
    public void registerOwner(IObjectOwner owner)
    {
        if (owners != null)
            owners.addElement(owner);
    }
    
    @Override
    public void unregisterOwner(IObjectOwner owner)
    {
        if (owners != null)
            owners.removeElement(owner);
    }
}
