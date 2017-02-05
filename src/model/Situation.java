/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;

/**
 *
 * @author Chaha
 */
public class Situation {
    public String name;
    public HashSet<Action> possibleActions;
    public HashSet<FreqEvent> freqEvents;
    public HashSet<Variable> variables;

    public Situation(String name) {
        this.name = name;
        possibleActions = new HashSet<>();
        freqEvents = new HashSet<>();
        variables = new HashSet<>();
    }
    
    
    
    public void addFreqEvent(int meanDayInterval, Event event){
        freqEvents.add(new FreqEvent(meanDayInterval, event));
    }
}
