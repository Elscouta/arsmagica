/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.PropertyDesc;
import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.Requirement;
import arsmagica.xml.PropertyContainer;
import arsmagica.xml.XMLError;
import java.util.ArrayList;
import java.util.List;

/**
 * A concrete event, currently being fired / displayed. They are generated
 * by @class EventDesc objects, that represent the general "types" of events.
 * 
 * An event if fully aware of its context, and calls to it will take into
 * account the objects it is supposed to apply to.
 * 
 * @author Elscouta
 */
public class Event extends PropertyContainer
{
    @Override public String getType() { return "event"; }
    
    private final World world;
    private final String text;
    private List<Option> options;
    
    public class Option extends Action
    {
        private final String text;
        
        public Option(String text, Effect effect, 
                      List<Requirement> requirements)
        {
            super(effect, requirements);
            this.text = text;
        }
        
        public String getText()
        {
            return text;
        }
        
        public void execute() throws XMLError
        {
            execute(world, Event.this);
        }
        
        public boolean isAvailable() throws XMLError
        {
            return isAvailable(world, Event.this);
        }
    }
    
    public Event(World world, String text)
            throws XMLError
    {
        super(world);
        this.text = text;
        this.world = world;
        this.options = new ArrayList<>();
    }
    
    public List<Option> getOptions()
    {
        return options;
    }
    
    public void addOption(String text, Effect effect, 
            List<Requirement> requirements)
    {
        options.add(new Option(text, effect, requirements));
    }
    
    public String getText()
    {
        return text;
    }
}
