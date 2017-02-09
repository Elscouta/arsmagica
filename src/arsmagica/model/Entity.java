/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.xml.PropertyContainer;
import arsmagica.xml.Ref;


/**
 *
 * @author Elscouta
 */
public class Entity extends PropertyContainer
{
    @Override public String getType() { return type; }
    
    private final String type;
    
    public Entity(World w, String type)
            throws Ref.Error
    {
        super(w);
        this.type = type;
    }
}
