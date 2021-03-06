/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.objects.Context;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.IObjectList;
import arsmagica.desc.IObjectSupplierDesc;
import arsmagica.control.WorldMgr;
import org.w3c.dom.Element;

/**
 * A method that allows to pick a random element from a list.
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
    public IObject get(WorldMgr w, IObject parent, Context context) 
            throws Ref.Error 
    {
        if (container == null) 
        {
            return w.getEntityMgr().getRandom(type);
        } 
        else 
        {
            return container.resolve(context).getRandom();
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
        public Expression<Integer> loadXML(Element e) 
                throws XMLError
        {
            final Expression<IObjectList> container = 
                    getChild(e, "list", new XMLBasicLoader.IObjectListLoader(), null);
            return (Context c) -> {
                try {
                    return container.resolve(c).getRandom().asInt().getValue();
                } catch (IObject.Mistyped err) {
                    throw new Ref.Error("Getting random integer from list failed: not an integer.", err);
                }
            };
        }
    }
}
