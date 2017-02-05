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
public abstract class Effect<T> {
    public String name;
    public boolean displayed;
    public GameRunner runner;
    
    public abstract boolean authorized();
    
    public abstract void apply(T object, int res);
}
