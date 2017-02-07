/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elscouta
 */
public class LoadEntities 
{
    private XMLFileLoader<EntityDesc> loader;
    
    public LoadEntities() 
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
        loader = new XMLFileLoader<>(store, "entity", 
                new EntityDesc.Loader(store));
    }
    
    @After
    public void tearDown() 
    {
    }

    @Test
    public void loadEntityEmpty() throws XMLError
    {
        List<EntityDesc> ret = loader.loadXML("entity_empty.xml");
        assertEquals(ret.size(), 1);
        assertEquals("test_entity", ret.get(0).getType());
        assertEquals(0, ret.get(0).getEvents().size());
        assertEquals(0, ret.get(0).getProperties().size());
    }
    
    @Test (expected=XMLError.class)
    public void loadEntityNoType() throws XMLError
    {
        List<EntityDesc> ret = loader.loadXML("entity_notype.xml");        
    }
    
    @Test
    public void loadEntityPropIntConst() throws XMLError
    {
        List<EntityDesc> ret = loader.loadXML("entity_propint_const.xml");
        EntityDesc e = ret.get(0);
        assertEquals(1, e.getProperties().size());
    }
    
    @Test (expected=XMLError.class)
    public void loadEntityPropNoType() throws XMLError
    {
        loader.loadXML("entity_prop_notype.xml");
    }
}
