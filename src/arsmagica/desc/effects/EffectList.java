/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.Ref;
import java.util.List;

/**
 * Represents a list of effects. Applying such an effect will simply 
 * apply all effects in the list.
 * 
 * @author Elscouta
 */
public class EffectList implements Effect
{
    private List<Effect> effects;
    
    private EffectList()
    {        
    }
    
    public EffectList(List<Effect> effects)
    {
        this.effects = effects;
    }
    
    @Override
    public void apply(WorldMgr world, Context context)
            throws Ref.Error
    {
        for (Effect e : effects)
            e.apply(world, context);
    }
}
