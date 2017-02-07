/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.IObjectList;
import arsmagica.xml.MethodIntLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class IObjectListDesc extends IObjectDesc
{
    private IObjectDesc type;
    private Expression<Integer> count;
    
    @Override public IObjectList create(EntityMgr eMgr, Entity parent)
    {
        return new IObjectList(parent, this);
    }

    public static class Loader extends XMLLoader<IObjectListDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new IObjectListDesc());
        }
        
        @Override
        public void fillObjectFromXML(IObjectListDesc obj, Element e) 
                throws XMLError
        {
            obj.type = getChild(e, "var", new IObjectDesc.Loader(store));
            obj.count = getChild(e, "count", new MethodIntLoader(store));
        }
    }
}
