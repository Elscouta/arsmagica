/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLNullLoader;
import arsmagica.model.objects.Context;
import arsmagica.model.objects.IObject;

/**
 *
 * @author Elscouta
 */
public class IObjectMapDesc extends IObjectDesc
{
    @Override 
    public String getType()
    {
        return "map";
    }
    
    @Override 
    public IObject create(WorldMgr w, IObject parent, Context context)
    {
        return null;
    }
    
    @Override
    public IObjectListDesc overwrite(String key, IObjectDesc other)
            throws Mistyped
    {
        if (!(other instanceof IObjectListDesc))
            throw new IObjectDesc.Mistyped(String.format(
                    "Attempting to overwrite list with type %s", 
                    other.getType()));
        
        return (IObjectListDesc) other;
    }
    
    public static class Loader extends XMLNullLoader<IObjectMapDesc>
    {
        public Loader(DataStore store)
        {
            super(() -> new IObjectMapDesc());
        }
    }
}
