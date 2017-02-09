/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class PropertyDesc 
{
    private String id;
    private IObjectDesc obj;

    /**
     * Returns the identifier of the property. Note that identifiers are 
     * stored in the property descriptions, but not in the object themselves.
     * 
     * @return The identifier
     */
    public String getID()
    {
        return id;
    }
    
    /**
     * Instantiates the associated based on the description. This simply
     * defers the call to the object description.
     * 
     * @param w The world module, to allow creation of
     * entities as-required.
     * @param parent The current context.
     * 
     * @return A concrete object.
     * @throws Ref.Error The XML that generated the description was ill-formed.
     */
    public IObject create(World w, IObjectStore parent)
            throws Ref.Error
    {
        return obj.create(w, parent);
    }
            
    public static class Loader extends XMLLoader<PropertyDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new PropertyDesc());
        }
        
        @Override
        public void fillObjectFromXML(PropertyDesc obj, Element e)
                throws XMLError
        {
            obj.id = getAttribute(e, "id");
            obj.obj = (new IObjectDesc.Loader(store)).loadXML(e);
        }
    }
}
