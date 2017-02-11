/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.control.WorldMgr;
import arsmagica.desc.EntityDesc;
import arsmagica.model.objects.Context;
import arsmagica.model.objects.Entity;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class PatchesTest 
{
    DataStore store;
    XMLFileLoader<EntityDesc> entityLoader;
    XMLFileLoader<EntityDesc.Patch> patchLoader;
    EntityDesc baseEntity;
    WorldMgr world;
    
    @Before
    public void iamInChargeNow() throws Exception
    {
        store = new DataStore("testdata/units/");
        entityLoader = new XMLFileLoader<>(store, "entity", 
                new EntityDesc.Loader(store));
        patchLoader = new XMLFileLoader<>(store, "patch",
                new EntityDesc.Patch.Loader(store));
        world = new WorldMgr(store);
        
        baseEntity = entityLoader.loadXML("entity_patchbase.xml").get(0);
        store.loadEntityDescFile("entity_patchbase.xml");        
    }
    
    private Entity loadPatch(String path)
            throws XMLError, Ref.Error
    {
        return patchLoader.loadXML(path)
                          .get(0)
                          .apply(baseEntity)
                          .create(world, null, Context.create());
    }
    
    @Test
    public void noPatch() throws Exception
    {
        Entity patchedEntity = baseEntity.create(world, null, Context.create());
        assertEquals(5, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(10, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());
        assertEquals(2, patchedEntity.getEvents().size());   

    }
    
    @Test
    public void overwriteInt() throws Exception
    {
        Entity patchedEntity = loadPatch("patch_overwriteint.xml");
        assertEquals(15, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(10, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());
        assertEquals(2, patchedEntity.getEvents().size());
    }
    
    @Test
    public void updateInt() throws Exception
    {
        Entity patchedEntity = loadPatch("patch_updateint.xml");
        assertEquals(5, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(25, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());        
        assertEquals(2, patchedEntity.getEvents().size());
    }
    
    @Test
    public void updateIntDouble() throws Exception
    {
        Entity patchedEntity = loadPatch("patch_updateint_double.xml");
        assertEquals(15, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(35, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());        
        assertEquals(2, patchedEntity.getEvents().size());
    }
    
    @Test
    public void updateString() throws Exception
    {
        Entity patchedEntity = loadPatch("patch_updatestring.xml");
        assertEquals(5, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(10, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello world", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());
        assertEquals(2, patchedEntity.getEvents().size());
    }
    
    @Test
    public void addEvents() throws Exception
    {
        Entity patchedEntity = loadPatch("patch_addevents.xml");
        assertEquals(5, patchedEntity.get("int_property").asInt().getValue());
        assertEquals(10, patchedEntity.get("int_property2").asInt().getValue());
        assertEquals("hello", patchedEntity.get("string_property").toString());
        assertEquals(5, patchedEntity.get("list_property").asList().getSize());
        assertEquals(3, (new Ref.Int("member_property.value", patchedEntity)).get().getValue());
        assertEquals(4, patchedEntity.getEvents().size());        
    }
           
}
