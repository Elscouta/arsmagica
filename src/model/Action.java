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
public class Action<T> {
    public String name;
    public String text;
    public HashSet<Effect> consequences;
    public HashSet<Effect> costs;

    public Action(String name, String text) {
        this.name = name;
        this.text = text;
        consequences = new HashSet<>();
        costs = new HashSet<>();
    }
    
    
    
    public boolean authorized(){
        for(Effect e : costs){
            if(!e.authorized()){
                return false;
            }
        }
        return true;
    }
    
    public void apply(T object, int res){
        for(Effect e : costs){
            e.apply(object, res);
        }
        for(Effect e : consequences){
            e.apply(object, res);
        }
    }
}
