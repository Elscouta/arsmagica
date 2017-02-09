/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.World;
import arsmagica.xml.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import org.w3c.dom.Element;

/**
 * The common inteface for all classes that wish to represent an effect.
 * 
 * @author Elscouta
 */
public abstract class Effect 
{
    /**
     * Applies the effect inside a given context.
     * @param world The main model class, for events that wish to access it
     * directly.
     * @param context The context used to fetch (local) variables.
     * @throws Ref.Error The description of the effect was incorrect, most 
     * likely because of an unbound or mistyped variable.
     */
    public abstract void apply(World world, Context context) 
            throws Ref.Error;
    
    public static class Loader extends XMLDirectLoader<Effect>
    {
        public Loader(DataStore store)
        {
            super(store);
        }
        
        private XMLDirectLoader<? extends Effect> getLoader(String method)
                throws XMLError
        {
            switch (method)
            {
                case "delta":       return new EffectDelta.Loader(store);
                case "destroy":     return new EffectDestroy.Loader(store);
                default: 
                    throw new XMLError(String.format("Unknown type: %s", method));
            }
        }   
        
        @Override public Effect loadXML(Element e)
                throws XMLError
        {
            String method = getAttribute(e, "type");
            Effect obj = getLoader(method).loadXML(e);
            return obj;
        }
    }
}
