/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.scripts;

import arsmagica.model.Entity;
import arsmagica.model.World;
import arsmagica.xml.DataStore;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class RunTest 
{
    @Test
    public void runAnts() throws Exception
    {
        DataStore store = new DataStore("testdata/ants/");
        store.load();
        World world = new World(store);
        Entity e = world.createEntity("nest");
        
        assertEquals(0, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(2, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(7, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(16, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(30, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(50, e.get("food").asInt().getValue());        
    }
    
    @Test
    public void runAntsShort() throws Exception
    {
        DataStore store = new DataStore("testdata/ants_short/");
        store.load();
        World world = new World(store);
        Entity e = world.createEntity("nest");
        
        assertEquals(0, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(2, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(7, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(16, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(30, e.get("food").asInt().getValue());
        world.nextDay();
        assertEquals(50, e.get("food").asInt().getValue());        
    }
}
