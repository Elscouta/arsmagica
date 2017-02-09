/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 * This effect destroys a property container (usually an entity). Note that
 * destruction of an entity will destroy other related entities and remove
 * any reference of it into list or mutable objects.
 * 
 * @author Elscouta
 */
public class EffectDestroy extends Effect
{
    Expression<IObject> ref;
    
    @Override
    public void apply(World world, IObjectStore context) throws XMLError 
    {
        ref.resolve(context).asObject().destroy();
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
            obj.ref = (new IObjectLoader()).loadXML(e);
        }
    }
}
