/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import java.util.HashSet;





/**
 *
 * @author Chaha
 */
public class GameData {
    public CharacterListModel characters;
    public Covenant covenant;
    public HashSet<Ability> abilities;
    public HashSet<Art> arts;

    
    
    public GameData() {
        characters = new CharacterListModel();
        covenant = new Covenant();
        abilities = new HashSet<>();
        arts = new HashSet<>();
    }
    
    
    
}
