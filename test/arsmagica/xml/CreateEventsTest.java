/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.EventDesc;
import arsmagica.desc.effects.Effect;
import arsmagica.model.Entity;
import arsmagica.model.Event;
import arsmagica.model.World;
import java.util.List;
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
    public void setUp() throws XMLError
    {
        store = new DataStore("test/arsmagica/xml/testdata/");
        world = new World(store);
        eventLoader = new XMLFileLoader<>(store, "event", 
                  new EventDesc.Loader(store));
        entityLoader = new XMLFileLoader<>(store, "entity",
                                           new EntityDesc.Loader(store));
        
        store.loadEntityDescFile("entity_propint_const.xml");
        testEntity = world.createEntity("test_entity");
        assertEquals(10, testEntity.get("test_property").asInt().getValue());
    }
    
    @Test
    public void createEventEmpty() throws XMLError
    {
        EventDesc desc = eventLoader.loadXML("event_empty.xml").get(0);
        Event e = desc.create(world, null);
        
        assertEquals("", e.getText());
        assertEquals("event", e.getType());
        assertEquals(0, e.getOptions().size());
    }
    
    @Test
    public void createEventEmptyWithSource() throws XMLError
    {
        EventDesc desc = eventLoader.loadXML("event_empty.xml").get(0);
        Event e = desc.create(world, testEntity);
        
        assertEquals(testEntity, e.get("source"));
        assertEquals(testEntity, e.get("test_entity"));
    }
    
    @Test
    public void createEventDeltaPosConst() throws XMLError
    {
        EventDesc desc = eventLoader.loadXML("event_delta_posconst.xml").get(0);
        Event e = desc.create(world, testEntity);
        
        e.getOptions().get(0).execute();
        
        assertEquals(20, testEntity.get("test_property").asInt().getValue());
    }
}
