/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

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
    
    public EntityMgr(DataStore store, World world)
    {
        this.store = store;
        this.world = world;
    }
    
    public Entity createNew(String type, IObject parent, Context context)
            throws Ref.Error
    {
        return store.getEntityDesc(type).create(world, parent, context);
    }
    
    public Entity getRandom(String type)
    {
        return null;
    }
}
