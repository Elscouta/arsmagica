/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Chaha
 */
public class FreqEvent {
    public int meanDayInterval;
    public Event event;

    public FreqEvent(int meanDayInterval, Event event) {
        this.meanDayInterval = meanDayInterval;
        this.event = event;
    }
    
    
}
