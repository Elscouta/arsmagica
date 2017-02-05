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
public class Event {
    public String name;
    public Effect effect;
    public java.time.LocalDateTime date;

    public Event(String name, Effect effect) {
        this.name = name;
        this.effect = effect;
    }
    
    
}
