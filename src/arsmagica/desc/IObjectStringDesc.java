/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.IObjectString;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.StringParser;
import arsmagica.xml.XMLError;
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
    public Expression<IObjectString> getInitializer()
    {
        return c -> new IObjectString(c, str);
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
            String initAttr = getAttribute(e, "init", null);
            if (initAttr != null) {
                if (getContent(e).equals("") == false)
                    throw new XMLError("init attribute specified in string : "
                            + "content must be empty.");
                
                obj.str = StringParser.eval(initAttr);
            }
            else
                obj.str = StringParser.eval(getContent(e));
        }
    }
}
