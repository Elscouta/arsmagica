/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.gui.Dialog;
import arsmagica.gui.DialogMgr;
import arsmagica.model.World;
import arsmagica.xml.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.XMLBasicLoader;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import java.util.List;
import org.w3c.dom.Element;

/**
 * This effect will ask the player to make a choice through a gui dialog.
 * This can be used to simply display a dialog with only one choice.
 * 
 * @author Elscouta
 */
public class EffectUserChoice extends Effect
{
    private boolean blocking;
    
    private String text;
    private List<OptionDesc> options;
    
    /**
     * The description of an option associated to the dialog. Such an option
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
                obj.text = getChild(e, "text", new XMLBasicLoader.ContentLoader());
                obj.requirements = getChildList(e, "requirement", 
                        new Requirement.Loader(store));
                obj.effects = getChildList(e, "effect",
                        new Effect.Loader(store));
            }
        }
    }
    
    @Override
    public void apply(World w, Context context)
    {
        Dialog d = w.getDialogMgr().createDialog(true, text);
        for (OptionDesc o : options)
        {
            Dialog.EnabledOracle oracle = () -> {
                for (Requirement r : o.requirements)
                {
                    if (!true)
                        return false;
                }
                return true;
            };
            
            EffectList effect = new EffectList(o.effects);
            
            d.addOption(o.text, effect, context, oracle);
        }
    }

    public static class Loader extends XMLLoader<EffectUserChoice>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new EffectUserChoice());
        }
        
        @Override
        public void fillObjectFromXML(EffectUserChoice obj, Element e)
                throws XMLError
        {
            obj.blocking = true;
            obj.text = getChild(e, "text", new ContentLoader());
            obj.options = getChildList(e, "option", new OptionDesc.Loader(store));
        }
    }
}
