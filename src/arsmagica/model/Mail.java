/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.control.MailMgr;
import arsmagica.control.WorldMgr;
import arsmagica.desc.effects.Effect;
import arsmagica.model.objects.Context;
import arsmagica.xml.Ref;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A mail received by the player, that ask to choose between or more 
 * options.
 * 
 * @author Elscouta
 */
public class Mail 
{
    /**
     * Creates a new dialog. This should only be used by the dialog manager.
     * @param text The text of the dialog.
     * @param mgr A link the class managing mail.
     */
    public Mail(MailMgr mgr, String text) 
    {
        this.mgr = mgr;
        this.text = text;
        this.options = new ArrayList<>();
        this.resolved = false;
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
        options.add(new OptionImpl(text, effect, context, oracle));
    }
    
    /**
     * Returns the text of the mail
     * 
     * @return The text of the mail
     */
    public String getText()
    {
        return text;
    }
    
    /**
     * Returns a list of options. These options can be accessed (for example
     * executing them), but the list itself should not be modified
     * 
     * @return A read-only view of the option list.
     */
    public List<Option> getOptions()
    {
        return Collections.unmodifiableList(options);
    }
    
    /**
     * Destroys this mail. This should only be done by an option being picked.
     */
    private void destroy() 
            throws Mail.InvalidOption
    {
        if (resolved)
            throw new Mail.InvalidOption("This mail has already been handled");

        resolved = true;
        mgr.destroyMail(this);
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
    
    /**
     * Public interface for the description of an option.
     */
    public interface Option
    {
        /**
         * Returns the text of the option.
         * 
         * @return The user-displayable text of the option.
         */
        public String getText();
        
        /**
         * Returns whether the option can be chosen.
         * 
         * @return true if the option is currently available for picking.
         */
        public boolean isAvailable();
        
        /**
         * Executes the actions associated with the option. This will 
         * remove (and invalidate) the associated mail object.
         * 
         * @param world A link to the world
         * 
         * @throws Ref.Error A reference in the effect code was incorrect.
         * @throws InvalidOption This option has already been selected or is
         * not available.
         */
        public void execute(WorldMgr world) throws Ref.Error, InvalidOption;
    }
    
    /**
     * Exception thrown when an option is wrongly selected. For example if the
     * option is not enabled, or if an option has already been chosen.
     */
    public class InvalidOption extends Exception
    {
        public InvalidOption(String msg) { super(msg); }
    }
        
    private final List<Mail> pendingMails = new ArrayList<>();
    private final List<Mail> blockingMails = new ArrayList<>();
   
    private final MailMgr mgr;
    private final String text;
    private final List<Option> options;
    private boolean resolved;
    
    private class OptionImpl implements Option
    {
        private final String text;
        private final Effect effect;
        private final Context context;
        private final EnabledOracle oracle;
        
        OptionImpl(String text, Effect effect, Context context, EnabledOracle oracle)
        {
            this.text = text;
            this.effect = effect;
            this.context = context;
            this.oracle = oracle;
        }

        @Override
        public String getText() 
        {
            return text;            
        }

        @Override
        public boolean isAvailable() 
        {
            try {
                return oracle.enabled();
            } catch (Ref.Error e) {
                // TODO: Some logging.
                return false;
            }
        }

        @Override
        public void execute(WorldMgr world) throws Ref.Error, InvalidOption
        {
            if (!isAvailable())
                throw new InvalidOption("This option is not available.");
            
            effect.apply(world, context);
            destroy();
        }
    }
    

}
