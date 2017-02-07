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
public class CovenantOption extends CreationModule{
    // type = Avantage ou Accroche
    
    public boolean isABoon(){
        return type.equals("Avantage");
    }
    
    public boolean isAHook(){
        return type.equals("Accroche");
    }

    public CovenantOption(String name, String cost, String type) {
        super.name = name;
        super.cost = cost;
        super.type = type;
    }
    
    public String toString(){
        return this.type+" "+this.cost+" : "+this.name;
    }
    
}
