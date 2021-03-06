/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.control;

import arsmagica.model.objects.Entity;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.IObjectOwner;
import arsmagica.xml.Ref;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class responsible for all event handling.
 * 
 * @author Elscouta
 */
public class EventMgr 
        implements TimeMgr.Listener, IObjectOwner
{
    private final WorldMgr world;
    private final Set<Entity> entities;
        
    public EventMgr(WorldMgr w)
    {
        world = w;
        entities = new HashSet<>();
    }

    /**
     * Registers an entity as a "live" entity, that can launch events.
     * All entities should be registered in that way.
     * 
     * @param e A newly created entity
     */
    public void registerEntity(Entity e)
    {
        assert (!entities.contains(e));
        
        e.registerOwner(this);
        entities.add(e);
    }
    
    /**
     * Executes all possible events
     */
    @Override
    public void openDay() 
    {
        List<Entity> eligibleEntities = new ArrayList<>(entities);
        for (Entity e : eligibleEntities)
        {
            try {
                e.fireDailyEvents();
            } catch (Ref.Error ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void notifyMemberDestroyed(IObject destroyed) 
    {
        assert (entities.contains((Entity) destroyed));
        
        entities.remove((Entity) destroyed);
    }
    
    
}
