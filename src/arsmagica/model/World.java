/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.xml.DataStore;

/**
 *
 * @author Elscouta
 */
public class World 
{
    private final EntityMgr entityMgr;
    
    public World(DataStore store)
    {
        entityMgr = new EntityMgr(store, this);
    }
    
    public EntityMgr getEntityMgr()
    {
        return entityMgr;
    }
    
}