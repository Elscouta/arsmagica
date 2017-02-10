/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.objects.Context;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import org.w3c.dom.Element;
import arsmagica.desc.IObjectSupplierDesc;

/**
 *
 * @author Elscouta
 */
public class MethodEntityLoader extends XMLDirectLoader< IObjectSupplierDesc >
{
    private final String type;
    
    private class New implements IObjectSupplierDesc
    {
        @Override public Entity get(WorldMgr w, IObject parent, Context context)
                throws Ref.Error
        {
            return w.getEntityMgr().createNew(type, parent, context);
        }
    }
    
    public MethodEntityLoader(DataStore store, String type)
    {
        super(store);
        this.type = type;
    }

    public XMLDirectLoader<? extends IObjectSupplierDesc> getLoader(String method)
            throws XMLError
    {
        switch (method)
        {
            case "new":         
                return new XMLNullLoader<>(() -> new New());
            case "get_random":  
                return new GetRandom.Loader(store, type);
            default: 
                throw new XMLError(String.format("Unknown method: %s", method));
        }
    }
    
    @Override
    public IObjectSupplierDesc loadXML(Element e) 
            throws XMLError 
    {
        String method = getAttribute(e, "method");
        return getLoader(method).loadXML(e);
    }
}
