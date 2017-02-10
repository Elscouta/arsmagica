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
import arsmagica.model.objects.IObjectList;
import arsmagica.xml.MethodIntLoader;
import arsmagica.xml.Ref;
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
 * A list is a lightweight container. Objects added to the list will consider
 * the parent and the context of the list as their own parent and context.
 * 
 * @author Elscouta
 */
public class IObjectListDesc extends IObjectDesc
{
    private IObjectDesc type;
    private Expression<Integer> count;
    
    @Override 
    public String getType()
    {
        return "list";
    }
    
    @Override 
    public IObjectList create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        IObjectList l = new IObjectList(parent, context);
        int rCount = count.resolve(context);
        
        for (int i = 0; i < rCount; i++)
            l.addElement(type.create(w, parent, context));
            
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
            IObjectDesc.Loader typeLoader = new IObjectDesc.Loader(store);
            String member_type = getAttribute(e, "member_type", null);
            
            if (member_type != null)
                obj.type = typeLoader.getLoader(member_type).loadXML(e);
            else
                obj.type = getChild(e, "var", new IObjectDesc.Loader(store));
            
            obj.count = getAttributeOrChild(e, "count", new MethodIntLoader(store));
        }
    }
}
