/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.effects.Effect;
import arsmagica.xml.Context;
import arsmagica.xml.Ref;

/**
 * TO IMPLEMENT
 * 
 * @author Elscouta
 */
public class Mail 
{
    /**
     * Creates a new dialog. This should only be used by the dialog manager.
     * @param text The text of the dialog.
     */
    protected Mail(String text) 
    {
    }
    
    /**
     * Adds an option to the dialog. Should only be used during the
     * construction of the dialog
     * @param text The text of the option.
     * @param effect The effect to apply when this option is selected.
     * @param context The context to use to fire the effect
     * @param oracle An oracle to determine if the option should be grayed.
     */
    public void addOption(String text, Effect effect, 
                          Context context, EnabledOracle oracle)
    {
        
    }
    
    /**
     * Used to determine whether an option must be grayed or not.
     */
    @FunctionalInterface
    public interface EnabledOracle
    {
        /**
         * Used to determine whether an option must be grayed or not.
         * 
         * @return Whether the option is enabled
         * @throws Ref.Error The predicate to determine whether the option
         * must be grayed failed because of an invalid reference.
         */
        public boolean enabled() throws Ref.Error;
    }
}
