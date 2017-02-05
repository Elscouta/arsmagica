/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Un effet qui produit plusieurs effets à la suite.
 * @author Chaha
 */
public class EffectList<T> extends Effect<T> {
    public ArrayList<Effect> list;
    
    @Override
    public void apply(T object, int res){
        for(Effect e : list){
            e.apply(object, res);
        }
    }
    
    @Override
    public boolean authorized(){
        for(Effect e : list){
            if (!e.authorized()){
                return false;
            }
        }
        return true;
    }
}
