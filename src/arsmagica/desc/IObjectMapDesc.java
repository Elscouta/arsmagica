/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLNullLoader;
import arsmagica.xml.IObjectStore;

/**
 *
 * @author Elscouta
 */
public class IObjectMapDesc extends IObjectDesc
{
    @Override public String getType()
    {
        return "map";
    }
    
    @Override public IObjectStore create(World w, IObjectStore context)
    {
        return null;
    }
    
    public static class Loader extends XMLNullLoader<IObjectMapDesc>
    {
        public Loader(DataStore store)
        {
            super(() -> new IObjectMapDesc());
        }
    }
}
