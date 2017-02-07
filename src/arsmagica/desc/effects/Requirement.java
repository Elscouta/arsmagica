/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.xml.DataStore;
import arsmagica.xml.XMLDirectLoader;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class Requirement 
{
    public static class Loader extends XMLDirectLoader<Requirement>
    {
        public Loader(DataStore store)
        {
            super(store);
        }
        
        @Override public Requirement loadXML(Element e)
        {
            return new Requirement();
        }
    }    
}
