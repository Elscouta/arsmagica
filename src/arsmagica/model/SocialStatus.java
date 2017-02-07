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
public class SocialStatus extends VirtueFlaw {

    public SocialStatus(String name, String cost, String type) {
        super(name, cost, type);
        super.subtype = "SocialStatus";
    }
    
}
