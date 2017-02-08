/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.IObjectString;
import arsmagica.xml.StringParser;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class IObjectStringDesc extends IObjectDesc
{
    private Expression<String> str;
    
    @Override
    public String getType()
    {
        return "string";
    }
    
    @Override 
    public IObjectString create(World w, IObjectStore context)
    {
        return new IObjectString(context, str);
    }
    
    public static class Loader extends XMLLoader<IObjectStringDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new IObjectStringDesc());
        }
        
        @Override
        public void fillObjectFromXML(IObjectStringDesc obj, Element e)
        {
            obj.str = StringParser.eval(getContent(e));
        }
    }
}
