/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.EventDesc;
import arsmagica.desc.effects.Requirement;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class RequirementsTest 
{
    private XMLFileLoader<EntityDesc> entityLoader;
    private XMLFileLoader<Requirement> requirementLoader;
    private DataStore store;
    private WorldMgr world;
    private Entity testEntity;


    @Before
    public void setUp() throws Exception
    {
        store = new DataStore("testdata/units/");
        world = new WorldMgr(store);
        requirementLoader = new XMLFileLoader<>(store, "requirement",
                new Requirement.Loader(store));
        
        store.loadEntityDescFile("entity_propint_const.xml");
        testEntity = world.createEntity("test_entity");
        assertEquals(10, testEntity.get("test_property").asInt().getValue());
    }
    
    @Test
    public void testArithmetic() throws Exception
    {
        List<Requirement> reqs = requirementLoader.loadXML("requirement_arith.xml");
        
        assertEquals(true, reqs.get(0).test(testEntity));
        assertEquals(true, reqs.get(1).test(testEntity));
        assertEquals(true, reqs.get(2).test(testEntity));
        assertEquals(true, reqs.get(3).test(testEntity));
        assertEquals(true, reqs.get(4).test(testEntity));
        assertEquals(false, reqs.get(5).test(testEntity));
        assertEquals(false, reqs.get(6).test(testEntity));
        assertEquals(false, reqs.get(7).test(testEntity));
        assertEquals(false, reqs.get(8).test(testEntity));
        assertEquals(false, reqs.get(9).test(testEntity));
        assertEquals(true, reqs.get(10).test(testEntity));
        assertEquals(true, reqs.get(11).test(testEntity));
        assertEquals(true, reqs.get(12).test(testEntity));
        assertEquals(true, reqs.get(13).test(testEntity));
        assertEquals(true, reqs.get(14).test(testEntity));
        assertEquals(false, reqs.get(15).test(testEntity));
        assertEquals(false, reqs.get(16).test(testEntity));
        assertEquals(false, reqs.get(17).test(testEntity));
        assertEquals(false, reqs.get(18).test(testEntity));
        assertEquals(false, reqs.get(19).test(testEntity));
    }
    
    @Test (expected = XMLError.class)
    public void testArithmeticNoOperator() throws Exception
    {
        requirementLoader.loadXML("requirement_noop.xml");
    }
    
    @Test (expected = XMLError.class)
    public void testArithmeticNoRight() throws Exception
    {
        requirementLoader.loadXML("requirement_noright.xml");
    }    
}
