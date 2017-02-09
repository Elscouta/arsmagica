/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;


/**
 * An helper class for any object that wish to own a static set of 
 * properties. Provides support for destruction notification.
 * 
 * FIXME: Way too many dumb casts there.
 * 
 * @author Elscouta
 */
public abstract class PropertyContainer 
        extends PropertyContext
        implements IObject, IObjectOwner
{
    @Override public PropertyContext getContext() { return this; }
    @Override public PropertyContainer asObject() { return this; }

    private IObjectList owners;
    
    public PropertyContainer()
    {
        super();
        
        owners = new IObjectList(this);
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
        
        for (IObject l : listeners)
            ((IObjectOwner) l).notifyMemberDestroyed(this);
    }
    
    /**
     * Adds the property, but also register it for destruction purposes.
     * @param key The identifier of the property
     * @param value Value to bind.
     */
    @Override
    public void addProperty(String key, IObject value)
    {
        super.addProperty(key, value);
        value.registerOwner(this);
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
            owners.addElement((IObject) owner);
    }
    
    @Override
    public void unregisterOwner(IObjectOwner owner)
    {
        if (owners != null)
            owners.removeElement((IObject) owner);
    }
}
