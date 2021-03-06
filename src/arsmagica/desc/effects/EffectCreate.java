/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.model.objects.IObjectList;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 *
 * @author Admin
 */
public class EffectCreate implements Effect 
{
    String type;
    Expression<IObjectList> destination;
    
    @Override
    public void apply(WorldMgr world, Context context)
        throws Ref.Error
        {
            IObjectList destinationList = destination.resolve(context);
            
            Entity e = world.getEntityMgr().createNew(type, 
                    destinationList.getParent(), destinationList.getContext());

            destinationList.addElement(e);
        }
    
    public static class Loader extends XMLLoader<EffectCreate>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EffectCreate());
        }
        
        @Override
        public void fillObjectFromXML(EffectCreate obj, Element e)
                throws XMLError
        {
            obj.type = getAttributeOrChild(e, "entity", new ContentLoader());
            obj.destination = getAttributeOrChild(e,"destination", new IObjectListLoader());
        }
    }
    
    
    }
