/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.IObjectStringDesc;

/**
 * Represents a string property.
 * Type of strings is "string".
 * 
 * Strings are usually immutable once created. They are generally defined
 * through a names file, but can also be explicitly defined through the
 * 'property' tag.
 * 
 * @author Elscouta
 */
public final class IObjectString implements IObject
{
    @Override public IObjectStore getContext() { return context; }
    @Override public String getType() { return "string"; }
    @Override public IObjectString asString() { return this; }

    private final IObjectStore context;
    private final IObjectStringDesc desc;
    
    public IObjectString(IObjectStore context, IObjectStringDesc desc)
    {
        this.context = context;
        this.desc = desc;
    }
}
