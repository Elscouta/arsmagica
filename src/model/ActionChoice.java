/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Set;

/**
 * Effet qui demande au joueur de choisir parmi plusieurs possibilités affichées
 * @author Chaha
 */
public class ActionChoice<T> extends Effect<T> {
    public Set<Action> choices;
    public String message;
    
    public void apply(T object, int res){
        // TO DO
    }
    
    public boolean authorized(){
        return true;
    }
}
