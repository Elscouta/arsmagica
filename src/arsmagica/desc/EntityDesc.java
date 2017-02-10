/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.model.objects.IObject;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLFileLoader;
import arsmagica.xml.XMLLoader;
import java.util.List;
import org.w3c.dom.Element;
import arsmagica.xml.Identifiable;
import arsmagica.model.objects.PropertyContainer;
import arsmagica.xml.Ref;
import java.util.ArrayList;
import arsmagica.model.objects.PropertyContext;

/**
 *
 * @author Elscouta
 */
public class EntityDesc implements Identifiable
{
    private String type;
    private List<PropertyDesc> properties;
    private List<EventDesc> events;
        
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
        Entity e = new Entity(w, type, events);
        
        if (parent != null)
        {
            e.addProperty("parent", parent);
            e.addProperty(parent.getType(), parent);
        }
            
        for (PropertyDesc p : properties)
            e.addProperty(p.getID(), p.create(w, e, e));
        
        return e;
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
            obj.type = getChild(e, "type", new ContentLoader());
            obj.properties = getChildList(e, "property", 
                                          new PropertyDesc.Loader(store));

            XMLLoader<EventDesc> eventLoader = new EventDesc.Loader(store);
            
            obj.events = getChild(e, "events",
                    new XMLFileLoader<>(store, "event", eventLoader),
                    new ArrayList<>());
        }
    }
}
