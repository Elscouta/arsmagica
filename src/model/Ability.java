/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Chaha
 */
public class Ability extends RollableStat{
    public String name;
    public String subtype;
    public boolean usableUntrained;
    public String domain;

    public Ability(String name, boolean usableUntrained, String domain) {
        this.name = name;
        this.usableUntrained = usableUntrained;
        this.domain = domain;
    }
    
    
}
