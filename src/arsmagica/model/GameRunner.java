/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import java.util.Random;
import java.util.Set;

/**
 *
 * @author Chaha
 */
public class GameRunner {
    public GameData gameData;
    public java.time.LocalDateTime currentTime;
    public Set<Variable> variables; // faire en sorte que les variables des situations y soient aussi ?

    public GameRunner(GameData gameData) {
        this.gameData = gameData;
    }
    
    
    public Variable findVar(String name){
        for(Variable v : variables){
            if(v.name.equalsIgnoreCase(name)){
                return v;
            }
        }
        return null;
    }
    
    
    public int findValue(RollableStat stat){
        return 0; // TO DO !
    }
}
