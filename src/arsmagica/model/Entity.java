/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.EventDesc;
import arsmagica.xml.PropertyContainer;
import arsmagica.xml.Ref;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * An entity, which is an abstract, XML-defined object, that has a set of
 * properties and generate daily events based on some probabilities.
 *
 * @author Elscouta
 */
public class Entity extends PropertyContainer
{
    @Override public String getType() { return type; }
    
    private final String type;
    private final World world;
    private final List<EventDesc> events;
    
    /**
     * Creates a new entity. This should only be called by EventDesc.create
     * Clients looking to create entities should use World.createEntity
     * Note that this constructor doesn't initialize properties.
     * 
     * @param w A link to the world
     * @param type The type of entity
     * @param events The list of events that are associated to this entity
     */
    public Entity(World w, String type, List<EventDesc> events)
    {
        super();
        this.world = w;
        this.type = type;
        this.events = events;
    }
    
    /**
     * Attemps to fire the possible events associated to the entity.
     * @throws Ref.Error Unbound or mistyped reference during event execution
     */
    public void fireDailyEvents()
            throws Ref.Error
    {
        for (EventDesc e : events)
        {
            double probability = e.getProbability(world, this);
            if (ThreadLocalRandom.current().nextDouble() < probability)
                e.execute(world, this);
        }
    }
}
