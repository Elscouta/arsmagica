/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.IObjectInt;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

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
    public void apply(World world, IObjectStore context)
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
            obj.value = getChild(e, "value", new ArithmeticLoader());
            obj.affectedObject = getChild(e, "property", new IObjectIntLoader());
        }
    }
}
