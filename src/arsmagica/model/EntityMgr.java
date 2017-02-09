/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.EntityDesc;
import arsmagica.xml.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.IObject;
import arsmagica.xml.PropertyContainer;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public class EntityMgr 
{
    private final DataStore store;
    private final World world;
    private final EventMgr eventMgr;
    
    public EntityMgr(DataStore store, EventMgr eventMgr, World world)
    {
        this.store = store;
        this.world = world;
        this.eventMgr = eventMgr;
    }
    
    public Entity createNew(String type, IObject parent, Context context)
            throws Ref.Error
    {
        EntityDesc desc = store.getEntityDesc(type);
        if (desc == null)
            throw new Ref.Error(String.format(
                    "Unknown entity type: %s",
                    type));
        Entity e = desc.create(world, parent, context);
        eventMgr.registerEntity(e);
        return e;
    }
    
    public Entity getRandom(String type)
    {
        return null;
    }
}
