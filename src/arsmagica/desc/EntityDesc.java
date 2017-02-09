/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLFileLoader;
import arsmagica.xml.XMLLoader;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Identifiable;
import arsmagica.xml.Ref;
import java.util.ArrayList;

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
        
    public Entity create(World w, IObjectStore parent)
            throws Ref.Error
    {
        Entity e = new Entity(w, type);
        
        if (parent != null)
        {
            e.addProperty("parent", parent);
            e.addProperty(parent.getType(), parent);
        }
            
        for (PropertyDesc p : properties)
            e.addProperty(p.getID(), p.create(w, e));
        
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
