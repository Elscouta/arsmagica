/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet qui crée une variable ou détruit une variable
 * @author Chaha
 */
public class VariableChange<T> extends Effect<T> {
    public String variable;
    public boolean create; // if false, it means the Effect destroys this Situation
    public GameRunner runner;
    
    
    @Override
    public boolean authorized() {
        return ((!create && !runner.findVar(variable).equals(null))|| create);
    }

    @Override
    public void apply(T object, int res) {
        if(create){
            // TO DO
        } else {
            runner.variables.remove(runner.findVar(variable));
        }
    }
    
}
