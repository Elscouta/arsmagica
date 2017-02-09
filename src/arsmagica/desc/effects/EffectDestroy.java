/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.World;
import arsmagica.xml.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.PropertyContainer;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.xml.PropertyContext;

/**
 * This effect destroys a property container (usually an entity). Note that
 * destruction of an entity will destroy other related entities and remove
 * any reference of it into list or mutable objects.
 * 
 * @author Elscouta
 */
public class EffectDestroy extends Effect
{
    Expression<PropertyContainer> ref;
    
    @Override
    public void apply(World world, Context context) 
            throws Ref.Error 
    {
        ref.resolve(context).destroy();
    }
    
    public static class Loader extends XMLLoader<EffectDestroy>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EffectDestroy());
        }
        
        @Override
        public void fillObjectFromXML(EffectDestroy obj, Element e)
                throws XMLError
        {
            obj.ref = (new PropertyContainerLoader()).loadXML(e);
        }
    }
}
