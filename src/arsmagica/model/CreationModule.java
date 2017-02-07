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
public class CreationModule {
    public String name;
    public String cost; // = Majeur, Mineur ou Neutre
    public String type;
    
    public int getIntCost(){
        int _cost = 0;
        if (type.equals("Vertu")|type.equals("Avantage")){
            if(cost.equals("Majeur")){
                _cost = 3;
            } if (cost.equals("Mineur")){
                _cost = 1;
            } 
        } else if (type.equals("Vice")|type.equals("Accroche")){
            if(cost.equals("Majeur")){
                _cost = -3;
            } else {_cost = -1;}
        }
        return _cost;
    }
    
}
