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
    public IObjectString create(WorldMgr w, IObject parent, Context context)
    {
        return new IObjectString(context, str);
    }
    
    @Override
    public IObjectStringDesc overwrite(String key, IObjectDesc other)
            throws Mistyped
    {
        if (!(other instanceof IObjectStringDesc))
            throw new Mistyped(String.format(
                    "Attempting overwrite of string with %s",
                    other.getType()));
        
        IObjectStringDesc otherObj = (IObjectStringDesc) other;
        IObjectStringDesc retObj = new IObjectStringDesc();

        retObj.str = (Context c) -> {
            String strV = str.resolve(c);
            IObjectString objV = new IObjectString(c, dummy -> strV);
            Context nc = Context.createWrapper(key, objV, c);
            return otherObj.str.resolve(nc);            
        };
               
        return retObj;
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
