/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 *
 * @author Elscouta
 */
public class EntitiesCreateTest 
{    
    private XMLFileLoader<EntityDesc> loader;
    private WorldMgr world;
    private DataStore store;

    @Before
    public void setUp() 
    {
        store = new DataStore("testdata/units/");
        loader = new XMLFileLoader<>(store, "entity", 
                new EntityDesc.Loader(store));
        world = new WorldMgr(store);
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
    
    @Test
    public void createEntityPropIntArithSame() throws Exception
    {
        store.loadEntityDescFile("entity_propint_arithsame.xml");
        
        Entity e = world.createEntity("test_entity");
        assertEquals(10, e.get("test_prop1").asInt().getValue());
        assertEquals(20, e.get("test_prop2").asInt().getValue());
    }
    
    @Test
    public void createEntityParent() throws Exception
    {
        store.loadEntityDescFile("entity_parent.xml");
        Entity parent = world.createEntity("test_parent");
        Entity child = (Entity) parent.get("child");
        
        assertNotEquals(null, child);
        
        assertEquals(10, parent.get("test_prop1").asInt().getValue());
        assertEquals(10, child.get("test_prop1").asInt().getValue());
        assertEquals(10, child.get("test_prop2").asInt().getValue());
        assertEquals(20, parent.get("test_prop2").asInt().getValue());
    }
    
    @Test
    public void createEntityPropList() throws Exception
    {
        store.loadEntityDescFile("entity_proplist.xml");
        Entity e = world.createEntity("test_entity");
        
        assertEquals(10, e.get("test_random_access").asInt().getValue());
    }
    
    @Test
    public void createEntityPropString() throws Exception
    {
        store.loadEntityDescFile("entity_propstring.xml");
        Entity e = world.createEntity("test_entity");
        
        assertEquals(10, e.get("i").asInt().getValue());
        assertEquals("Hello: 10", e.get("s1").toString());
        assertEquals("Hello: 10 is love", e.get("s2").toString());
    }
}
