/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.model.objects.IObject;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.Identifiable;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;

import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class PropertyDesc implements Identifiable
{
    private String id;
    private IObjectDesc obj;

    /**
     * Returns the identifier of the property. Note that identifiers are 
     * stored in the property descriptions, but not in the object themselves.
     * 
     * @return The identifier
     */
    @Override
    public String getIdentifier()
    {
        return id;
    }
    
    /**
     * Returns the initializer of the property. 
     * 
     * @return The initializer
     */
    public Expression<? extends IObject> getInitializer()
    {
        return obj.getInitializer();
    }
    
    /**
     * Returns the type of the property.
     * 
     * @return The type
     */
    public String getType()
    {
        return obj.getType();
    }
    
    /**
     * Instantiates the associated based on the description. This simply
     * defers the call to the object description.
     * 
     * @param w The world module, to allow creation of
     * entities as-required.
     * @param parent The object owning that property.
     * @param context The context in which free variables must be interpreted.
     * 
     * @return A concrete object.
     * @throws Ref.Error The XML that generated the description was ill-formed.
     */
    public IObject create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        return obj.create(w, parent, context);
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
