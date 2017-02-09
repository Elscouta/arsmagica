/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * Contexts are short lived stores.
 * FIXME: fix this freaking naming mess.
 * 
 * @author Elscouta
 */
public class IObjectContext implements IObjectStore 
{
    @Override public String getType() { return "context"; }
    @Override public IObjectStore getContext() { return this; }
    
    private String key;
    private IObject value;

    private IObjectContext(String key, IObject value)
    {
        this.key = key;
        this.value = value;
    }
    
    public static IObjectContext createWrapper(String key, IObject value)
    {
        return new IObjectContext(key, value);
    }
    
    @Override
    public IObject get(String k)
            throws IObject.Unknown
    {
        if (key.equals(k))
            return value;
        else
            throw new IObject.Unknown(k, this);
    }
    
}
