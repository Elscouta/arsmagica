/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import javax.swing.DefaultListModel;


/**
 *
 * @author Chaha
 */
public class CharacterListModel extends DefaultListModel<String>{
    public HashMap<String,AnyCharacter> characters;

    public CharacterListModel() {
         characters = new HashMap<>();
    }

    
    
    
    
    
}
