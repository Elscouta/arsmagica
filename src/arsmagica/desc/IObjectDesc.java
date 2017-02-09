/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public abstract class IObjectDesc 
{
    /**
     * Instantiates a property based on the description.
     * 
     * @param w The world module, to allow creation of
     * entities as-required.
     * @param context The context in which the object is created. This might
     * not be the direct parent: it is the context in which the object
     * look for variables.
     * 
     * @return A concrete property.
     * @throws XMLError The XML that generated the description was ill-formed.
     */
    public abstract IObject create(World w, IObjectStore context)
            throws Ref.Error;
    
    /**
     * Returns the type of the object description. This must be the same
     * type as the one of the object created by the create method.
     * @return The type of created objects.
     */
    public abstract String getType();
    
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
                case "map":     throw new XMLError("Map type is not supported.");
                default:        l = new IObjectEntityDesc.Loader(store, type); 
            }
            
            return l.loadXML(e);
        }
    }    
}
