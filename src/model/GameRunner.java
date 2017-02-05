/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;
import java.util.Set;

/**
 *
 * @author Chaha
 */
public class GameRunner {
    public GameData gameData;
    public java.time.LocalDateTime currentTime;
    public Set<Situation> situations;
    public Set<Variable> variables; // faire en sorte que les variables des situations y soient aussi ?
    public EventQueue eventQueue;

    public GameRunner(GameData gameData) {
        this.gameData = gameData;
    }
    
    
    
    public Situation findSit(String name){
        for(Situation s : situations){
            if(s.name.equalsIgnoreCase(name)){
                return s;
            }
        }
        return null;
    }
    
    public Variable findVar(String name){
        for(Variable v : variables){
            if(v.name.equalsIgnoreCase(name)){
                return v;
            }
        }
        return null;
    }
    
    public Event findEvent(String name){
        for(Event e : eventQueue.queue){
            if(e.name.equalsIgnoreCase(name)){
                return e;
            }
        }
        return null;
    }
    
    public int findValue(RollableStat stat){
        return 0; // TO DO !
    }
}
