/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.desc.effects.Effect;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import arsmagica.desc.effects.Requirement;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.model.objects.IObject;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import java.util.List;
import org.w3c.dom.Element;
import arsmagica.model.objects.PropertyContext;

/**
 * The description of an event, loaded from an event file. An event description
 * follows the following structure:
 * 
 * <pre>
 * {@code
 * <property type=''>...</property>
 * <property type=''>...</property>
 * <effect type=''>...</effect>
 * <effect type=''>...</effect>
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
    private List<PropertyDesc> properties;
    private Expression<Double> probability;
    private List<Effect> effects;
    private List<Requirement> requirements;
            
    public double getProbability(WorldMgr world, IObject source)
            throws Ref.Error
    {
        PropertyContext context = PropertyContext.create();
        context.addProperty("source", source);
        context.addProperty(source.getType(), source);
        
        for (Requirement r : requirements)
        {
            if (r.test(context) == false)
                return 0d;
        }
        
        return probability.resolve(context);
    }
    
    public void execute(WorldMgr world, Entity source)
            throws Ref.Error
    {
        PropertyContext eventContext = PropertyContext.create();
        
        if (source != null)
        {
            eventContext.addProperty("source", source);
            eventContext.addProperty(source.getType(), source);
        }
        
        for (PropertyDesc p : properties)
            eventContext.addProperty(p.getID(), p.create(world, null, eventContext));
        
        for (Effect effect : effects)
            effect.apply(world, eventContext);
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
            obj.probability = getChild(e, "probability", new ArithmeticDoubleLoader());
            obj.effects = getChildList(e, "effect",
                                       new Effect.Loader(store));
            obj.requirements = getChildList(e, "requirement",
                                            new Requirement.Loader(store));
        }
    }
}
