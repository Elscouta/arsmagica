/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Chaha
 */
public class MageCharacter extends AnyCharacter {
    public AM.House house;
    public HashMap<Art,Integer> arts;
//    public int creo;
//    public int intellego;
//    public int muto;
//    public int perdo;
//    public int rego;
//    public int animal;
//    public int aquam;
//    public int auram;
//    public int corpus;
//    public int herbam;
//    public int ignem;
//    public int imaginem;
//    public int mentem;
//    public int terram;
//    public int vim;
    
    @Override
    public boolean isMage(){
        return true;
    }
}
