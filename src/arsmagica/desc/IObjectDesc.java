/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import arsmagica.xml.XMLSubLoader;
import org.w3c.dom.Element;
import arsmagica.xml.IObject;

/**
 *
 * @author Elscouta
 */
public abstract class IObjectDesc 
{
    /**
     * Instantiates a property based on the description.
     * 
     * @param eMgr The entity management module, to allow creation of
     * entities as-required.
     * @param parent The entity that owns the property.
     * 
     * @return A concrete property.
     * @throws XMLError The XML that generated the description was ill-formed.
     */
    public abstract IObject create(EntityMgr eMgr, Entity parent)
            throws XMLError;
    
    
    public static class Loader extends XMLDirectLoader<IObjectDesc>
    {
        public Loader(DataStore store)
        {
            super(store);
        }
        
        @Override
        public IObjectDesc loadXML(Element e)
                throws XMLError
        {
            String type = getAttribute(e, "type");
            XMLLoader<? extends IObjectDesc> l;
            
            switch (type)
            {
                case "int":     l = new IObjectIntDesc.Loader(store);      break;
                case "string":  l = new IObjectStringDesc.Loader(store);   break;
                case "list":    l = new IObjectListDesc.Loader(store);     break;
                case "map":     l = new IObjectMapDesc.Loader(store);      break;
                default:        l = new IObjectEntityDesc.Loader(store, type); 
            }
            
            return l.loadXML(e);
        }
    }    
}
