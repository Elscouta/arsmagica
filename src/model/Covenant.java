/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;

/**
 *
 * @author Chaha
 */
public class Covenant {
    public String name;
    public AM.Tribunal tribunal;
    public HashSet<CovenantOption> options;
    public HashSet<Laboratory> laboratories;
}
