/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet qui crée un Event ou détruit un Event
 * @author Chaha
 */
public class EventChange<T> extends Effect<T> {
    public String event;
    public boolean create; // if false, it means the Effect destroys this Situation
    
    
    @Override
    public boolean authorized() {
        return ((!create && !runner.findEvent(event).equals(null))|| create);
    }

    @Override
    public void apply(T object, int res) {
        if(create){
            //runner.eventQueue.add(new Event());
        } else {
            runner.eventQueue.queue.remove(runner.findEvent(event));
        }
    }
    
}
