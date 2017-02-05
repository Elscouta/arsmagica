/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Un effet qui tire un des Effect de sa liste et qui l'applique
 * @author Chaha
 */
public class EffectAmongN<T> extends Effect<T>{
    public ArrayList<Effect> effects;
    public int[] odds;
   

    public EffectAmongN(ArrayList<Effect> effects, int[] odds) {
        this.effects = effects;
        this.odds = odds;
    }
    
    public int total(){
        int total = 0;
        for(int i=0;i<odds.length;i++){
            if(effects.get(i).authorized()){
                total+=odds[i];
            }
        }
        return total;
    }
    
    @Override
    public void apply(T object, int res){
        int total = total();
        int chosen = AM.random.nextInt(total+1);
        int marker = 0;
        int sum = 0;
        while(sum<chosen){
            sum+=odds[marker];
            if(sum>chosen){ effects.get(marker).apply(object, res);}
            marker++;
        }
    }
    
    public boolean authorized(){
        for(Effect e : effects){
            if(e.authorized()){
                return true;
            }
        }
        return false;
    }
}
