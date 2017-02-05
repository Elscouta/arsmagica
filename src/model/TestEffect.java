/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet qui fait un test aléatoire sur une valeur, puis déclenche un Effect en lui passant le résultat.
 * @author Chaha
 */
public class TestEffect<T> extends Effect<T> {
    public RollableStat rolledStat;
    public Effect next;
    
    @Override
    public boolean authorized() {
        return true; // TO DO
    }

    @Override
    public void apply(T object, int res) {
        int stat = runner.findValue(rolledStat);
        next.apply(object, AM.roll()-stat);
    }
    
}
