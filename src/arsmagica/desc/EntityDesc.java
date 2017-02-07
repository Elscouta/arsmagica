/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLFileLoader;
import arsmagica.xml.XMLLoader;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import arsmagica.xml.IObject;

/**
 *
 * @author Elscouta
 */
public class EntityDesc 
{
    private String type;
    private List<PropertyDesc> properties;
    private List<EventDesc> events;
    
    public void initProperties(EntityMgr eMgr, Entity parent,
            Map<String, IObject> pMap)
            throws XMLError
    {
        for (PropertyDesc p : properties)
            pMap.put(p.getID(), p.create(eMgr, parent));
    }
    
    public String getType()
    {
        return type;
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
            obj.type = getAttribute(e, "type");
            obj.properties = getChildList(e, "property", 
                                          new PropertyDesc.Loader(store));

            XMLLoader<EventDesc> eventLoader = new EventDesc.Loader(store);
            
            obj.events = getChild(e, "events",
                    new XMLFileLoader<>(store, "event", eventLoader));
        }
    }
}
