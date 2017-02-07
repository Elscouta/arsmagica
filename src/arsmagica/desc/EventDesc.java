/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.Requirement;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import java.util.List;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class EventDesc
{
    private String text;
    private List<PropertyDesc> properties;
    private List<OptionDesc> options;
    
    private static class OptionDesc
    {
        String text;
        List<Requirement> requirements;
        List<Effect> effects;
        
        static class Loader extends XMLLoader<OptionDesc>
        {
            Loader(DataStore store)
            {
                super(store, () -> new OptionDesc());
            }
            
            @Override public void fillObjectFromXML(OptionDesc obj, Element e)
                    throws XMLError
            {
                obj.text = getChild(e, "text", new ContentLoader());
                obj.requirements = getChildList(e, "requirement", 
                        new Requirement.Loader(store));
                obj.effects = getChildList(e, "effect",
                        new Effect.Loader(store));
            }
        }
    }
    
    public static class Loader extends XMLLoader<EventDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EventDesc());
        }
        
        @Override public void fillObjectFromXML(EventDesc obj, Element e)
                throws XMLError
        {
            obj.properties = getChildList(e, "property",
                                          new PropertyDesc.Loader(store));
            obj.text = getChild(e, "text", new ContentLoader());
            obj.options = getChildList(e, "option", 
                                       new OptionDesc.Loader(store));
        }
    }
}
