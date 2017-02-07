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
public class Art extends RollableStat{
    public String name;
    private String techniqueOrForm;
    public String description;

    public Art(String name, String techniqueOrForm) {
        this.name = name;
        this.techniqueOrForm = techniqueOrForm;
    }
    
    public boolean isAForm(){
        return techniqueOrForm.equals("Forme");
    }
    
    public boolean isATechnique(){
        return techniqueOrForm.equals("Technique");
    }
}
