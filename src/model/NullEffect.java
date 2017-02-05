/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Un effet par défaut où il ne se passe rien. Singleton
 * @author Chaha
 */
public class NullEffect<T> extends Effect<T>{

    private static NullEffect instance = new NullEffect();
    
    public static NullEffect getInstance(){
        return instance;
    }
    
    private NullEffect() {
    }
    
    
    
    @Override
    public boolean authorized() {
        return true;
    }

    @Override
    public void apply(T object, int res) {}
    
}
