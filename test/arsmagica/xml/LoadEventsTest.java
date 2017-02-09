/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.Settings;
import arsmagica.desc.EntityDesc;
import arsmagica.desc.EventDesc;
import arsmagica.model.Entity;
import arsmagica.model.World;
import java.util.List;
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
public class LoadEventsTest 
{
    private XMLFileLoader<EventDesc> loader;
    private World world;
    private Entity testEntity;
    
    @Before
    public void setUp() throws Exception
    {
        DataStore store = new DataStore("test/arsmagica/xml/testdata/");
        loader = new XMLFileLoader<>(store, "event", 
                new EventDesc.Loader(store));
        world = new World(store);
        
        store.loadEntityDescFile("entity_propint_const.xml");
        testEntity = world.createEntity("test_entity");
        assertEquals(10, testEntity.get("test_property").asInt().getValue());
    }
    
    @Test
    public void loadEventEmpty() throws XMLError
    {
        List<EventDesc> ret = loader.loadXML("event_empty.xml");
        assertEquals(ret.size(), 1);
    }
    
    @Test (expected=XMLError.class)
    public void loadEventNoType() throws XMLError
    {
        loader.loadXML("event_notext.xml");        
    }
    
    @Test (expected=XMLError.class)
    public void loadEventNoProb() throws XMLError
    {
        loader.loadXML("event_noprob.xml");
    }
    
    @Test
    public void loadEventProbConst() throws Exception
    {
        EventDesc e = loader.loadXML("event_empty.xml").get(0);
        assertEquals(1, e.getProbability(world, testEntity), Settings.FLOAT_PRECISION);
    }
    
    @Test
    public void loadEventProbSource() throws Exception
    {
        EventDesc e = loader.loadXML("event_probsource.xml").get(0);
        assertEquals(0.5, e.getProbability(world, testEntity), Settings.FLOAT_PRECISION);        
    }
}
