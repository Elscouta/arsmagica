/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.model.objects.Entity;
import arsmagica.model.objects.IObject;
import arsmagica.xml.DataStore;
import arsmagica.xml.Identifiable;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLFileLoader;
import arsmagica.xml.XMLLoader;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class EntityDesc implements Identifiable
{
    private String type;
    private Map<String, PropertyDesc> properties;
    private List<EventDesc> events;
    private List<Action> actions;
        
    public String getType()
    {
        return type;
    }
    
    @Override public String getIdentifier()
    {
        return type;
    }
    
    public Entity create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        Entity e = new Entity(w, type, events, actions);
        
        if (parent != null)
        {
            e.addProperty("parent", parent);
            e.addProperty(parent.getType(), parent);
        }
            
        for (PropertyDesc p : properties.values())
        {
            String key = p.getIdentifier();
            e.addProperty(p.getIdentifier(), p.create(w, e, e));
        }
        
        return e;    }
    
    public static class Loader extends XMLLoader<EntityDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EntityDesc());
        }
        
        @Override
        public void fillObjectFromXML(EntityDesc obj, Element e) 
                throws XMLError
        {
            obj.type = getAttributeOrChild(e, "type", new ContentLoader());
            obj.properties = getChildMap(e, "property", 
                                         new PropertyDesc.Loader(store));

            XMLLoader<EventDesc> eventLoader = new EventDesc.Loader(store);
            
            obj.events = getChild(e, "events",
                    new XMLFileLoader<>(store, "event", eventLoader),
                    new ArrayList<>());
            obj.actions = new ArrayList<>();
        }
    }

    /**
     * A patch that can be applied to an EntityDesc, generating a new EntityDesc
     * in the process.
     *
     * @author Elscouta
     */
    public static class Patch 
    {
        private Map<String, PropertyDesc> properties;
        private List<EventDesc> events;
        private List<Action> actions;
        
        /**
         * Applies the patch to an entity description
         * 
         * @param base The base description
         * @return The modified description
         * @throws XMLError Impossible to apply patch due to mismatched types.
         */
        public EntityDesc apply(EntityDesc base)
                throws XMLError
        {            
            EntityDesc modified = new EntityDesc() 
            {                                
                @Override 
                public String getIdentifier() { return "__custom_desc__"; }
            
                @Override
                public Entity create(WorldMgr w, IObject parent, Context parentContext)
                    throws Ref.Error
                {
                    return super.create(w, parent, parentContext);
                }
            };
                    
            modified.properties = new HashMap<>(base.properties);
            int size = modified.properties.size();
            
            for (PropertyDesc patchedProperty : properties.values())
            {
                String id = patchedProperty.getIdentifier();
                
                if (modified.properties.containsKey(id))
                {
                    PropertyDesc oldValue = modified.properties.get(id);
                    PropertyDesc newValue = oldValue.overwrite(patchedProperty);
                    modified.properties.put(id, newValue);
                }
                else 
                    modified.properties.put(id, patchedProperty);
            }
            
            modified.events = new ArrayList<>(base.events);
            for (EventDesc e : events)
                modified.events.add(e);
            
            modified.actions = new ArrayList<>(base.actions);
            for (Action a : actions)
                modified.actions.add(a);
            
            return modified;
        }
    
        public static class Loader extends XMLLoader<Patch>
        {
            public Loader(DataStore store)
            {
                super(store, () -> new Patch());
            }
        
            @Override
            public void fillObjectFromXML(Patch obj, Element e) 
                    throws XMLError
            {
                obj.properties = getChildMap(e, "property", 
                                             new PropertyDesc.Loader(store));

                XMLLoader<EventDesc> eventLoader = new EventDesc.Loader(store);
            
                obj.events = getChild(e, "events",
                        new XMLFileLoader<>(store, "event", eventLoader),
                        new ArrayList<>());
                obj.actions = new ArrayList<>();
            }
        }
    }
}
