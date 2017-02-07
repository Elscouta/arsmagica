/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.EntitySupplierDesc;
import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class MethodEntityLoader extends XMLDirectLoader< EntitySupplierDesc >
{
    private final String type;
    
    private class New implements EntitySupplierDesc
    {
        @Override public Entity get(EntityMgr eMgr)
        {
            return eMgr.createNew(type);
        }
    }
    
    private class GetRandom implements EntitySupplierDesc
    {
        @Override public Entity get(EntityMgr eMgr)
        {
            return eMgr.getRandom(type);
        }
    }
    
    public MethodEntityLoader(DataStore store, String type)
    {
        super(store);
        this.type = type;
    }

    private XMLDirectLoader<? extends EntitySupplierDesc> getLoader(String method)
            throws XMLError
    {
        switch (method)
        {
            case "new":         
                return new XMLNullLoader<>(() -> new New());
            case "get_random":  
                return new XMLNullLoader<>(() -> new GetRandom());
            default: 
                throw new XMLError(String.format("Unknown method: %s", method));
        }
    }
    
    @Override
    public EntitySupplierDesc loadXML(Element e) 
            throws XMLError 
    {
        String method = getAttribute(e, "method");
        return getLoader(method).loadXML(e);
    }
}
