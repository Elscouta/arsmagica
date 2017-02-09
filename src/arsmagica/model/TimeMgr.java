/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The class responsible for the passage of time. Object can subscribe to this
 * manager in order to be informed about day changes.
 * 
 * @author Elscouta
 */
public class TimeMgr 
{
    int days;
    private final List<Listener> listeners;
    
    public TimeMgr()
    {
        days = 0;
        listeners = new ArrayList<>();
    }
   
    /**
     * Adds a listener to the 
     * @param l 
     */
    public void addListener(Listener l)
    {
        listeners.add(l);
    }
    
    /**
     * Sends the game into the next day.
     * @throws NotReady one subscribed object wasn't ready for the day change.
     */
    public void nextDay()
            throws NotReady
    {        
        for (Listener l : listeners)
            l.closeDay();
        
        days++;
        
        for (Listener l : listeners)
            l.openDay();
    }
    
    /**
     * Interface that can be implemented by any class that wish to
     * be informed about day changes.
     */
    public interface Listener
    {
        /**
         * Called when a day is about to end.
         * @throws NotReady The day should not have ended: the module is not
         * ready for a day change.
         */
        default void closeDay() throws NotReady {}
        
        /**
         * A new day is starting.
         */
        default void openDay() {}
    }
    
    /**
     * Exception to be thrown when a day is ending but one object is not ready
     * for such a change.
     */
    public static class NotReady extends Exception
    {
        public NotReady(String msg) { super(msg); }
    }
}
