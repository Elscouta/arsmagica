/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.xml.IObject;
import arsmagica.xml.IObjectOwner;
import arsmagica.xml.Ref;
import java.util.HashSet;
import java.util.Set;

/**
 * The class responsible for all event handling.
 * 
 * @author Elscouta
 */
public class EventMgr 
        implements TimeMgr.Listener, IObjectOwner
{
    private final World world;
    private final Set<Entity> entities;
        
    public EventMgr(World w)
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
        for (Entity e : entities)
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
