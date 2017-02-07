/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.Requirement;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.XMLError;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a possible action, as an effect associated to a set of 
 * requirements. The action is not contextualized: in order to execute it, 
 * the current world and context must be provided.
 * 
 * @author Elscouta
 */
public class Action 
{
    private final Effect effect;
    private final List<Requirement> requirements;

    /**
     * Constructs an action without requirements
     * @param effect The effect of the action
     */
    public Action(Effect effect)
    {
        this.effect = effect;
        this.requirements = new ArrayList<>();
    }
    
    /**
     * Constructs an action with requirements
     * @param effect The effect of the action
     * @param requirements The list of requirements necessary for the action. 
     */
    public Action(Effect effect, List<Requirement> requirements)
    {
        this.effect = effect;
        this.requirements = requirements;
    }
    
    /**
     * Executes the action in the given context. 
     * FIXME: Should throw if requirements are not fulfilled.
     * 
     * @param world The world
     * @param context The context used to fetch local variables.
     * @throws XMLError The effect was misformed.
     */
    public void execute(World world, IObjectStore context)
            throws XMLError
    {
        effect.apply(world, context);
    }
    
    /**
     * Checks whether all requirements are fulfilled.
     * FIXME: Implement me
     * 
     * @param world The world
     * @param context The context used to fetch local variables.
     * @return true if all requirements are fulfilled.
     * @throws XMLError The requirements were misformed.
     */
    public boolean isAvailable(World world, IObjectStore context)
            throws XMLError
    {
        return true;
    }
}
