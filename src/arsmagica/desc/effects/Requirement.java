/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.xml.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLDirectLoader;
import arsmagica.xml.XMLError;
import org.w3c.dom.Element;

/**
 * This class represents an abstract requirement description, without an 
 * associated context. With a context, you can check whether the requirement
 * is fulfilled.
 * 
 * @author Elscouta
 */
public abstract class Requirement 
{
    /**
     * Checks whether the requirement is fulfilled
     * 
     * @param context The context in which the variables must be fetched
     * @return Whether the requirement is fulfilled.
     * @throws Ref.Error One of the variables of the context was not
     * present or mistyped.
     */
    public abstract boolean test(Context context) throws Ref.Error;
    
    public static class Loader extends XMLDirectLoader<Requirement>
    {
        public Loader(DataStore store)
        {
            super(store);
        }
        
        public XMLDirectLoader<? extends Requirement> getLoader(String type)
                throws XMLError
        {
            switch (type)
            {
                case "lower": 
                case "lt":
                    return new RequirementComparison.LoaderXML(store, (a, b) -> a < b);
                case "higher":
                case "greater":
                case "gt":
                    return new RequirementComparison.LoaderXML(store, (a, b) -> a > b);
                case "lower-or-equal":
                case "lte":
                    return new RequirementComparison.LoaderXML(store, (a, b) -> a <= b);
                case "higher-or-equal":
                case "greater-or-equal":
                case "gte":
                    return new RequirementComparison.LoaderXML(store, (a, b) -> a >= b);
                case "equal":
                case "==":
                    return new RequirementComparison.LoaderXML(store, (a, b) -> a.equals(b));
                default:
                    throw new XMLError("Unknown requirement type.");
            }
        }
        
        @Override 
        public Requirement loadXML(Element e)
                throws XMLError
        {
            String type = getAttribute(e, "type", null);
            if (type == null)
                return (new RequirementComparison.LoaderString(store))
                        .loadString(getContent(e));
            else
                return getLoader(type).loadXML(e);
        }
    }    
}
