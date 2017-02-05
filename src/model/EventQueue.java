/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Chaha
 */
public class EventQueue {
    // list of events sorted by date
    public ArrayList<Event> queue;
    
    public Event next(){
        return queue.get(0);
    }
    
    public void add(Event e){
        int i = 0;
        while (queue.get(i).date.isBefore(e.date)){
            i++;
        }
        queue.add(i, e);
    }
}
