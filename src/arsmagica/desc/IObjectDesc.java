/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.PropertyContainer;
import arsmagica.xml.Expression;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

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
     * @param parent The parent that will store the object. This can be null
     * if the object is not intended for XML storage.
     * @param context The context in which the object is created. This might
     * not be the direct parent: it is the context in which the object
     * look for variables.
     * 
     * @return A concrete property.
     * @throws Ref.Error The XML that generated the description was ill-formed.
     */
    public abstract IObject create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error;
    
    /**
     * Returns the initializer of the object. This is only necessary to be
     * implemented for integer and string properties.
     * 
     * @return the initializer
     */
    public Expression<? extends IObject> getInitializer()
    {
        throw new RuntimeException("no initializer available.");
    }
    
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
        
        public XMLDirectLoader<? extends IObjectDesc> getLoader(String type)
                throws XMLError
        {
            switch (type)
            {
                case "int":     return new IObjectIntDesc.Loader(store);      
                case "string":  return new IObjectStringDesc.Loader(store);   
                case "list":    return new IObjectListDesc.Loader(store);     
                case "map":     throw new XMLError("Map type is not supported.");
                default:        return new IObjectEntityDesc.Loader(store, type); 
            }            
        }
        
        @Override
        public IObjectDesc loadXML(Element e)
                throws XMLError
        {
            String type = getAttribute(e, "type");
            XMLDirectLoader<? extends IObjectDesc> l = getLoader(type);
                        
            return l.loadXML(e);
        }
    }    
}
