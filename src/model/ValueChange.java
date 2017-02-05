/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet qui change la valeur d'une variable
 * @author Chaha
 */
public class ValueChange<T> extends Effect<T>{
    public Changeable changedValue; // stat, global variable, resource, situation variable
    public int modifier;
    
    @Override
    public boolean authorized() {
        return true;
        
        // TO DO !
    }

    @Override
    public void apply(T object, int res) {
        // TO DO
    }
    
}
