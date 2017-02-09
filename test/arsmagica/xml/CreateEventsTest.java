/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.EventDesc;
import arsmagica.model.Entity;
import arsmagica.model.World;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class CreateEventsTest 
{
    private XMLFileLoader<EntityDesc> entityLoader;
    private XMLFileLoader<EventDesc> eventLoader;
    private DataStore store;
    private World world;
    private Entity testEntity;


    @Before
    public void setUp() throws Exception
    {
        store = new DataStore("test/arsmagica/xml/testdata/");
        world = new World(store);
        eventLoader = new XMLFileLoader<>(store, "event", 
                  new EventDesc.Loader(store));
        
        store.loadEntityDescFile("entity_propint_const.xml");
        testEntity = world.createEntity("test_entity");
        assertEquals(10, testEntity.get("test_property").asInt().getValue());
    }
    
    @Test
    public void createEventEmpty() throws Exception
    {
        EventDesc desc = eventLoader.loadXML("event_empty.xml").get(0);
    }
    
    @Test
    public void createEventEmptyWithSource() throws Exception
    {
        EventDesc desc = eventLoader.loadXML("event_empty.xml").get(0);
    }
    
    @Test
    public void createEventDeltaPosConst() throws Exception
    {
        EventDesc desc = eventLoader.loadXML("event_delta_posconst.xml").get(0);
    }
}
