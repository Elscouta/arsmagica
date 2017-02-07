/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.MethodEntityLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.xml.IObjectStore;

/**
 *
 * @author Elscouta
 */
public class IObjectEntityDesc extends IObjectDesc
{
    private EntitySupplierDesc initializer;
    private String type;
    
    @Override public IObjectStore create(EntityMgr e, Entity parent)
    {
        return initializer.get(e);
    }
    
    public static class Loader extends XMLLoader<IObjectEntityDesc>
    {
        public Loader(DataStore store, String type)
        {
            super(store, () -> new IObjectEntityDesc());
        }
        
        @Override 
        public void fillObjectFromXML(IObjectEntityDesc obj, Element e)
                throws XMLError
        {
            obj.type = getAttribute(e, "type");
            obj.initializer = getChild(e, "init", 
                    new MethodEntityLoader(store, obj.type));
        }
    }
}
