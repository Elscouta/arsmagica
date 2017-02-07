/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.Settings;
import arsmagica.desc.EntityDesc;
/**
 * The main data stores. Stores all the gamedata associated to their keys.
 * 
 * @author Elscouta
 */
public class DataStore 
{
    private final XMLStore<EntityDesc> entityDescs;
    
    public DataStore()
    {
        entityDescs = new XMLStore<>(new EntityDesc.Loader(this));
    }

    public void load()
    {
        try
        {
            entityDescs.load(Settings.ENTITIES_DESCS_PATH, "entity", this);
        }
        catch (XMLError e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }    
    
    public EntityDesc getEntityDesc(String key)
    {
        return entityDescs.get(key);
    }
}
