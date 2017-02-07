/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.IObjectListDesc;

/**
 * Represents a list of properties, sharing the same type.
 * 
 * @author Elscouta
 */
public class IObjectList implements IObject
{
    @Override public IObjectStore getContext() { return context; }
    @Override public String getType() { return "list"; }
    @Override public IObjectList asList() { return this; }
    
    private final IObjectStore context;
    private final IObjectListDesc desc;
    
    public IObjectList(IObjectStore context, IObjectListDesc desc)
    {
        this.context = context;
        this.desc = desc;
    }
}
