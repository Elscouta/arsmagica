/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.Entity;
import arsmagica.model.World;
import org.w3c.dom.Element;
import arsmagica.desc.IObjectSupplierDesc;

/**
 * FIXME: Remove Entity cast
 */
class GetRandom implements IObjectSupplierDesc
{
    private final String type;
    private Expression<IObjectList> container;
    
    private GetRandom(String type)
    {
        this.type = type;
    }
    
    @Override
    public IObject get(World w, IObjectStore parent) throws XMLError 
    {
        if (container == null) 
        {
            return w.getEntityMgr().getRandom(type);
        } 
        else 
        {
            return container.resolve(parent).getRandom();
        }
    }
            
    public static class Loader extends XMLLoader<GetRandom>
    {
        public Loader(DataStore store, String type)
        {
            super(store, () -> new GetRandom(type));
        }

        @Override
        public void fillObjectFromXML(GetRandom obj, Element e)
                throws XMLError
        {
            obj.container = getChild(e, "list", new XMLBasicLoader.IObjectListLoader(), null);
        }
    }
    
    public static class IntLoader extends XMLDirectLoader< Expression<Integer> >
    {
        public IntLoader(DataStore store)
        {
            super(store);
        }
        
        @Override 
        public Expression<Integer> loadXML(Element e) throws XMLError
        {
            final Expression<IObjectList> container = 
                    getChild(e, "list", new XMLBasicLoader.IObjectListLoader(), null);
            return c -> container.resolve(c).getRandom().asInt().getValue();
        }
    }
}
