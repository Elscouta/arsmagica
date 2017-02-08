/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.IObjectList;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.MethodIntLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 * The description of a list. A list definition follows the following
 * structure:
 * 
 * <pre>
 * {@code
 * <... type='list'>
 *   <var type='elementType'>
 *     (initialization of elementType)
 *   </var>
 *   <count method='intMethod'>
 *     (parameters of intMethod)
 *   </count>
 * </...>
 * }
 * </pre>
 * 
 * @author Elscouta
 */
public class IObjectListDesc extends IObjectDesc
{
    private IObjectDesc type;
    private Expression<Integer> count;
    
    @Override public String getType()
    {
        return "list";
    }
    
    @Override public IObjectList create(World w, IObjectStore context)
            throws XMLError
    {
        IObjectList l = new IObjectList(context, type.getType());
        int rCount = count.resolve(context);
        
        for (int i = 0; i < rCount; i++)
            l.addElement(type.create(w, context));
            
        return l;
    }

    public static class Loader extends XMLLoader<IObjectListDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new IObjectListDesc());
        }
        
        @Override
        public void fillObjectFromXML(IObjectListDesc obj, Element e) 
                throws XMLError
        {
            obj.type = getChild(e, "var", new IObjectDesc.Loader(store));
            obj.count = getChild(e, "count", new MethodIntLoader(store));
        }
    }
}
