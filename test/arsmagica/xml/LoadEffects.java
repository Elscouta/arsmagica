/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntityDesc;
import arsmagica.desc.effects.Effect;
import arsmagica.model.Entity;
import arsmagica.model.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Elscouta
 */
public class LoadEffects 
{
    private XMLFileLoader<EntityDesc> entityLoader;
    private XMLFileLoader<Effect> effectLoader;
    private Entity testEntity;
    private World world;
    private DataStore store;
    
    @Before
    public void setUp() throws XMLError
    {
        store = new DataStore("test/arsmagica/xml/testdata/");
        effectLoader = new XMLFileLoader<>(store, "effect", 
                                           new Effect.Loader(store));
        entityLoader = new XMLFileLoader<>(store, "entity",
                                           new EntityDesc.Loader(store));
        world = new World(store);
        
        store.loadEntityDescFile("entity_propint_const.xml");
        testEntity = world.createEntity("test_entity");
        assertEquals(10, testEntity.get("test_property").asInt().getValue());
    }
    
    @Test
    public void loadEffectDeltaPosConst() throws XMLError
    {
        Effect e = effectLoader.loadXML("effect_delta_posconst.xml").get(0);
        e.apply(world, testEntity);
        
        assertEquals(20, testEntity.get("test_property").asInt().getValue());
    }
}
