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
import arsmagica.xml.IObjectInt;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;
import arsmagica.xml.PropertyContext;

/**
 * Represents an effect that modifies an (integer) variable by adding delta.
 * 
 * @author Elscouta
 */
public class EffectDelta extends Effect
{
    private Expression<Integer> value;
    private Expression<IObjectInt> affectedObject;
    
    @Override
    public void apply(World world, Context context)
            throws Ref.Error
    {
        IObjectInt obj = affectedObject.resolve(context);
        obj.applyDelta(value.resolve(context));
    }
    
    public static class Loader extends XMLLoader<EffectDelta>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EffectDelta());
        }
        
        @Override
        public void fillObjectFromXML(EffectDelta obj, Element e)
                throws XMLError
        {
            obj.value = getAttributeOrChild(e, "value", new ArithmeticLoader());
            obj.affectedObject = getAttributeOrChild(e, "property", new IObjectIntLoader());
        }
    }
}
