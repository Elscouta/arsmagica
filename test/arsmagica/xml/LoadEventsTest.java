/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.EventDesc;
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
    
    public LoadEventsTest() 
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
        DataStore store = new DataStore("test/arsmagica/xml/testdata/");
        loader = new XMLFileLoader<>(store, "event", 
                new EventDesc.Loader(store));
    }
    
    @After
    public void tearDown() 
    {
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
}
