/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

/**
 *
 * @author Chaha
 */
public class VirtueFlaw extends CreationModule{
    
    // type = Vice ou Vertu
    public String subtype; // Hermetic, Supernatural, SocialStatus, General, Personality, Story
    
    
    public VirtueFlaw(String name,String cost,String type){
        super.name = name;
        super.cost = cost;
        super.type = type;
        
    }

    public boolean isAVirtue() {
        return type.equals("Vertu");
    }
    
    public boolean isAFlaw(){
        return type.equals("Vice");
    }
}
