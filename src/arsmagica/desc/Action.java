/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.Requirement;
import arsmagica.model.objects.Context;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import java.util.List;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLLoader;
import org.w3c.dom.Element;

/**
 * Represents a possible action, as an effect associated to a set of 
 * requirements. The action is not contextualized: in order to execute it, 
 * the current world and context must be provided.
 * 
 * @author Elscouta
 */
public class Action 
{
    private String description;
    private List<Effect> effects;
    private List<Requirement> requirements;

    /**
     * Returns a shorthand description of the action, usable to display the
     * user on a single line or so.
     * 
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }
    
    
    /**
     * Executes the action in the given context. 
     * FIXME: Should throw if requirements are not fulfilled.
     * 
     * @param world The world
     * @param context The context used to fetch local variables.
     * @throws Ref.Error The effect was misformed.
     */
    public void execute(WorldMgr world, Context context)
            throws Ref.Error
    {
        for (Effect e : effects)
            e.apply(world, context);
    }
    
    /**
     * Checks whether all requirements are fulfilled.
     * 
     * @param world The world
     * @param context The context used to fetch local variables.
     * @return true if all requirements are fulfilled.
     * @throws Ref.Error The requirements were misformed.
     */
    public boolean isAvailable(WorldMgr world, Context context)
            throws Ref.Error
    {
        for (Requirement r : requirements)
        {
            if (!r.test(context))
                return false;
        }
        
        return true;       
    }
    
    public static class Loader extends XMLLoader<Action>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new Action());
        }
        
        @Override
        public void fillObjectFromXML(Action obj, Element e)
                throws XMLError
        {
            obj.description = 
                    getChild(e, "description", new ContentLoader());
            obj.effects = 
                    getChildList(e, "effect", new Effect.Loader(store));
            obj.requirements = 
                    getChildList(e, "requirement", new Requirement.Loader(store));
        }
    }
}
