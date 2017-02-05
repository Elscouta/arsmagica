/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;
import java.util.Set;



/**
 * Class that contains all the enum values
 * @author Chaha
 */
public final class AM {
    public static Random random = new Random();
    public static int diceSize = 10;
    public static Set<CovenantOption> covOptions;

    private AM() {
    }
    
    public static int roll(){
        return random.nextInt(diceSize);
    }
    
    public enum House {
        BJORNAER ("Bjornaer"), BONISAGUS ("Bonisagus"),
        CRIAMON ("Criamon"), EXMISCELLANEA ("Ex Miscellanea"),
        FLAMBEAU ("Flambeau"), GUERNICUS ("Guernicus"),
        JERBITON ("Jerbiton"), MERCERE ("Mercere"),
        MERINITA ("Merinita"), TREMERE ("Tremere"),
        TYTALUS ("Tytalus"), VERDITIUS ("Verditius");
        private final String houseName;
        
        House(String houseName){
            this.houseName = houseName;
        }
        @Override
        public String toString(){
            return houseName;
        }
        
        public static House enumOfString(String string){
            House h = null;
            for(House _h : House.values()){
                if(_h.houseName.equals(string)){
                    h = _h;
                }
            }
            return h;
        }
    }
    
    public enum Tribunal {
        HIBERNIAN ("Tribunal Hibernien"),
        LOCHLEGLEAN ("Tribunal de Loch Leglean"), STONEHENGE ("Tribunal de Stonehenge"),
        IBERIAN ("Tribunal Ibérien"), PROVENCAL ("Tribunal Provençal"),
        NORMANDY ("Tribunal de Normandie"), RHINE ("Tribunal du Rhin"),
        GREATERALPS ("Tribunal des Hautes Alpes"), ROMAN ("Tribunal Romain"),
        TRANSYLVANIAN ("Tribunal Transylvanien"), NOVGOROD ("Tribunal de Novgorod"),
        THEBES ("Tribunal de Thebes"), LEVANT ("Tribunal du Levant");
        private final String tribunalName;
        
        Tribunal (String tribunalName){
            this.tribunalName = tribunalName;
        }
        @Override
        public String toString(){
            return tribunalName;
        }
        
        public static Tribunal enumOfString(String string){
            Tribunal t = null;
            for(Tribunal _t : Tribunal.values()){
                if(_t.tribunalName.equals(string)){
                    t = _t;
                }
            }
            return t;
        }
    }
}
