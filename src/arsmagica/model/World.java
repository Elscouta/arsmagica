/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.gui.DialogMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.PropertyContext;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public class World 
{
    private final EntityMgr entityMgr;
    private final DialogMgr dialogMgr;
    
    public World(DataStore store)
    {
        entityMgr = new EntityMgr(store, this);
        dialogMgr = new DialogMgr(this);
    }
    
    public EntityMgr getEntityMgr()
    {
        return entityMgr;
    }
    
    public DialogMgr getDialogMgr()
    {
        return dialogMgr;
    }
    
    public Entity createEntity(String type)
            throws Ref.Error
    {
        return entityMgr.createNew(type, null, PropertyContext.create());
    }
    
}
