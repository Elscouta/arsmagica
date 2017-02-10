/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.IObjectString;
import arsmagica.xml.StringParser;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.model.objects.PropertyContext;
import arsmagica.xml.XMLError;

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
    public IObjectString create(WorldMgr w, IObject parent, Context context)
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
                throws XMLError
        {
            obj.str = StringParser.eval(getContent(e));
        }
    }
}
