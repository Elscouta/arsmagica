/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet qui crée une Situation ou qui détruit une Situation
 * @author Chaha
 */
public class SituationChange<T> extends Effect<T>{
    public GameRunner runner;
    public String situation;
    public boolean create; // if false, it means the Effect destroys this Situation 
    
    @Override
    public boolean authorized() {
        return ((!create && !runner.findSit(situation).equals(null))|| create);
    }

    @Override
    public void apply(T object, int res) {
        if(create){
            // TO DO
        } else {
            runner.situations.remove(runner.findSit(situation));
        }
    }
    
}
