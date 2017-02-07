/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.EffectList;
import arsmagica.desc.effects.Requirement;
import arsmagica.model.Event;
import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import java.util.List;
import org.w3c.dom.Element;

/**
 * The description of an event, loaded from an event file. An event description
 * follows the following structure:
 * 
 * <pre>
 * {@code
 * <property type=''>...</property>
 * <property type=''>...</property>
 * <text>Event text (to be shown to user)</text>
 * <option>...</option>
 * <option>...</option>
 * }
 * </pre>
 * 
 * See @class OptionDesc for the description of the option format and 
 * @class PropertyDesc for the description of the property format.
 * 
 * @author Elscouta
 */
public class EventDesc
{
    private String text;
    private List<PropertyDesc> properties;
    private List<OptionDesc> options;
    
    /**
     * The description of an option associated to an event. Such an option
     * follows the following structure.
     * 
     * <pre>
     * {@code
     * <text>Option text (to be shown to the user)</text>
     * <requirement type=''>...</requirement>
     * <requirement type=''>...</requirement>
     * <effect type=''>...</effect>
     * <effect type=''>...</effect>
     * }
     * </pre>
     * 
     * See @class Effect for the format used to describe an effect and @class
     * Requirement for the format used to describe a requirement.
     */
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
    
    public List<PropertyDesc> getProperties()
    {
        return properties;
    }
    
    public Event create(World world, IObjectStore source)
            throws XMLError
    {
        Event e = new Event(world, text, properties);
        
        if (source != null)
        {
            e.addProperty("source", source);
            e.addProperty(source.getType(), source);
        }
        
        for (OptionDesc o : options)
            e.addOption(o.text, new EffectList(o.effects), o.requirements);
        
        return e;
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
