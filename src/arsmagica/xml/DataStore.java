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
    private final String gamedataPath;
    
    /**
     * Constructs a new data store.
     * 
     * @param gamedataPath The path to the directory that is assumed to contain
     * all gamedata. All xml-written paths will be relative to that directory.
     */
    public DataStore(String gamedataPath)
    {
        entityDescs = new XMLStore<>(new EntityDesc.Loader(this));
        this.gamedataPath = gamedataPath;
    }

    /**
     * Loads everything in the gamedata directory, based on paths hardcoded
     * into the Settings class.
     */
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
    
    /**
     * Returns the directory that contains the currently used game data.
     * @return The path to the gamedata directory
     */
    public String getGamedataPath()
    {
        return gamedataPath;
    }
    
    /**
     * Returns the entity description associated to the given type
     * @param key The type of the entity
     * @return The description of the entity
     */
    public EntityDesc getEntityDesc(String key)
    {
        return entityDescs.get(key);
    }
}
