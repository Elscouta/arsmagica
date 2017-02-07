/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.io.IOException;
/**
 * The main data stores. Stores all the gamedata associated to their keys.
 * 
 * @author Elscouta
 */
public class DataStore 
{
    private DataStore()
    {
//        try
        {
        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//            System.exit(1);
//        }
    }
    
    /**
     * Instantiates a new data store
     * @return the newly created store.
     */
    static public DataStore create()
    {
        return new DataStore();
    }
}
