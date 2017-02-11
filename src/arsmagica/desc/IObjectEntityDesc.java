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
import arsmagica.xml.MethodEntityLoader;
import arsmagica.model.objects.PropertyContainer;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.model.objects.PropertyContext;

/**
 *
 * @author Elscouta
 */
public class IObjectEntityDesc extends IObjectDesc
{
    private IObjectSupplierDesc initializer;
    private String type;
    
    @Override 
    public String getType() 
    {
        return type;
    }
    
    @Override 
    public IObject create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        return initializer.get(w, parent, context);
    }
    
    @Override
    public IObjectDesc overwrite(String key, IObjectDesc other)
            throws Mistyped
    {
        if (!(other instanceof IObjectEntityDesc))
            throw new IObjectDesc.Mistyped(String.format(
                    "Attempting to overwrite entity with type %s", 
                    other.getType()));
        
        return other;
    }
            
    public static class Loader extends XMLLoader<IObjectEntityDesc>
    {
        private final String type;
        
        public Loader(DataStore store, String type)
        {
            super(store, () -> new IObjectEntityDesc());
            this.type = type;
        }
        
        @Override 
        public void fillObjectFromXML(IObjectEntityDesc obj, Element e)
                throws XMLError
        {
            obj.type = type;
            
            String initMethod = getAttribute(e, "init", null);
            MethodEntityLoader loader = new MethodEntityLoader(store, obj.type);
            
            if (initMethod != null)
                obj.initializer = loader.getLoader(initMethod).loadXML(e);
            else
                obj.initializer = getChild(e, "init", 
                        new MethodEntityLoader(store, obj.type), 
                        null);
            
            if (obj.initializer == null)
                obj.initializer = loader.getLoader("new").loadXML(e);
        }
    }
}
