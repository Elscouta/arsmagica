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
import arsmagica.model.objects.PropertyContext;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.Identifiable;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLBasicLoader;
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
    private List<PropertyDesc> properties;
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
    
    private Entity create(WorldMgr w, IObject parent, 
                          Context parentContext, 
                          Map<String, Expression<? extends IObject>> oldValues)
            throws Ref.Error
    {        
        Entity e = new Entity(w, type, events, actions);
        
        if (parent != null)
        {
            e.addProperty("parent", parent);
            e.addProperty(parent.getType(), parent);
        }
            
        for (PropertyDesc p : properties)
        {
            String key = p.getID();
            Context pContext = e;
            if (oldValues.containsKey(key))
            {
                IObject oldValue = oldValues.get(key).resolve(pContext);
                pContext = Context.createWrapper(key, oldValue, e);
            }

            e.addProperty(p.getID(), p.create(w, e, pContext));
        }
        
        return e;
    }
    
    public Entity create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        return create(w, parent, context, new HashMap<>());
    }
    
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
            obj.properties = getChildList(e, "property", 
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
        private List<PropertyDesc> properties;
        private List<EventDesc> events;
        private List<Action> actions;
        
        /**
         * Applies the patch to an entity description
         * 
         * @param base The base description
         * @return The modified description
         */
        public EntityDesc apply(EntityDesc base)
        {
            final Map<String, Expression<? extends IObject>> oldValues = new HashMap<>();
            
            EntityDesc modified = new EntityDesc() 
            {                                
                @Override 
                public String getIdentifier() { return "__custom_desc__"; }
            
                @Override
                public Entity create(WorldMgr w, IObject parent, Context parentContext)
                    throws Ref.Error
                {
                    return super.create(w, parent, parentContext, oldValues);
                }
            };
                    
            modified.properties = new ArrayList<>(base.properties);
            int size = modified.properties.size();
            
            for (PropertyDesc patchedProperty : properties)
            {
                boolean overwritten = false;
                
                for (int i = 0; i < size; i++)
                {
                    PropertyDesc modProperty = modified.properties.get(i);
                    if (modProperty.getID().equals(patchedProperty.getID()))
                    {
                        modified.properties.set(i, patchedProperty);
                        
                        if (modProperty.getType().equals("string") ||
                            modProperty.getType().equals("int")) {
                            oldValues.put(patchedProperty.getID(),
                                          modProperty.getInitializer());
                        }
                        
                        overwritten = true;
                        break;
                    }                        
                }
                
                if (!overwritten)
                    modified.properties.add(patchedProperty);
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
                obj.properties = getChildList(e, "property", 
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
