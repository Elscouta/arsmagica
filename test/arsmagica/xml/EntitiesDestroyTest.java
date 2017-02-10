/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.effects.Effect;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 *
 * @author Elscouta
 */
public class EntitiesDestroyTest
{    
    private XMLFileLoader<EntityDesc> entityLoader;
    private XMLFileLoader<Effect> effectLoader;
    private Effect destroy;
    private WorldMgr world;
    private DataStore store;
    
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Before
    public void setUp() throws XMLError
    {
        store = new DataStore("testdata/units/");
        
        effectLoader = new XMLFileLoader<>(store, "effect", 
                                           new Effect.Loader(store));
        entityLoader = new XMLFileLoader<>(store, "entity", 
                new EntityDesc.Loader(store));
        
        destroy = effectLoader.loadXML("effect_destroy.xml").get(0);
        world = new WorldMgr(store);
    }
    
    @Test
    public void destructListMember() throws Exception
    {
        store.loadEntityDescFile("entity_proplist2.xml");
        
        Entity parent = world.createEntity("test_parent");
        
        assertEquals(5, parent.get("test_list").asList().getSize());
        parent.get("test_list").asList().getRandom().asObject().destroy();
        assertEquals(4, parent.get("test_list").asList().getSize());
        parent.get("test_list").asList().getRandom().asObject().destroy();
        assertEquals(3, parent.get("test_list").asList().getSize());
        parent.get("test_list").asList().getRandom().asObject().destroy();
        assertEquals(2, parent.get("test_list").asList().getSize());
        parent.get("test_list").asList().getRandom().asObject().destroy();
        assertEquals(1, parent.get("test_list").asList().getSize());
        parent.get("test_list").asList().getRandom().asObject().destroy();
        assertEquals(0, parent.get("test_list").asList().getSize());
    }
    
    @Test
    public void destructProperty() throws Exception
    {
        store.loadEntityDescFile("entity_proplist2.xml");
        
        Entity parent = world.createEntity("test_parent");
        
        assertEquals(5, parent.get("test_list").asList().getSize());
        parent.get("test_list")
              .asList()
              .getRandom()
              .asObject()
              .get("test_child")
              .asObject()
              .destroy();
        assertEquals(4, parent.get("test_list").asList().getSize());        
    }
    
    @Test
    public void applyEffect() throws Exception
    {
        store.loadEntityDescFile("entity_proplist2.xml");
        
        Entity parent = world.createEntity("test_parent");
        
        assertEquals(5, parent.get("test_list").asList().getSize());
        destroy.apply(world, parent.get("test_list")
                                   .asList()
                                   .getRandom()
                                   .asObject());
        assertEquals(4, parent.get("test_list").asList().getSize());                
    }
}
