/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.xml.DataStore;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.XMLError;

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
    
    public Entity createNew(String type, IObjectStore parent)
            throws XMLError
    {
        return store.getEntityDesc(type).create(world, parent);
    }
    
    public Entity getRandom(String type)
    {
        return null;
    }
}
