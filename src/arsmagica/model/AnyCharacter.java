/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Chaha
 */
public class AnyCharacter {
    public String name;
    public int intelligence;
    public int perception;
    public int strength;
    public int stamina;
    public int presence;
    public int communication;
    public int dexterity;
    public int quickness;
    public List<VirtueFlaw> virtuesFlaws;
    public SocialStatus socialStatus;
    public HashMap<Ability,Integer> abilities;
    public HashMap<Personality,Integer> personalities;
    public int reputation;
    public int age;
    public int decrepitude;
    public int warpingScore;
    public int warpingPoints;
    
    public Location location;

    public AnyCharacter(){
    }
    
    public boolean isMage(){
        return false;
    }
    
}
