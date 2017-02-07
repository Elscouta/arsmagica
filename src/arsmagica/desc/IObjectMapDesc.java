/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLNullLoader;
import arsmagica.xml.IObjectStore;

/**
 *
 * @author Elscouta
 */
public class IObjectMapDesc extends IObjectDesc
{
    @Override public IObjectStore create(EntityMgr eMgr, Entity parent)
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
