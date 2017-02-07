/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.model.Entity;
import arsmagica.model.World;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class CreateEntities 
{    
    private XMLFileLoader<EntityDesc> loader;
    private World world;
    private DataStore store;
    
    public CreateEntities() 
    {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
    }
    
    @Before
    public void setUp() 
    {
        store = new DataStore("test/arsmagica/xml/testdata/");
        loader = new XMLFileLoader<>(store, "entity", 
                new EntityDesc.Loader(store));
        world = new World(store);
    }
    
    @After
    public void tearDown() 
    {
    }

    @Test
    public void createEntityEmpty() throws Exception
    {
        store.loadEntityDescFile("entity_empty.xml");
        Entity e = world.createEntity("test_entity");
        assertEquals("test_entity", e.getType());        
    }
    
    @Test
    public void createEntityPropIntConst() throws Exception
    {
        store.loadEntityDescFile("entity_propint_const.xml");
        Entity e = world.createEntity("test_entity");
        assertEquals(10, e.get("test_property").asInt().getValue());
    }
    
    @Test
    public void createEntityPropIntRandom() throws Exception
    {
        store.loadEntityDescFile("entity_propint_random.xml");
        Entity e = world.createEntity("test_entity");
        assertEquals(8, e.get("test_property").asInt().getValue());
    }
}
