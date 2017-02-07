/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.desc.EntityDesc;
import arsmagica.xml.XMLError;
import java.util.HashMap;
import java.util.Map;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.PropertyContainer;


/**
 *
 * @author Elscouta
 */
public class Entity extends PropertyContainer
{
    @Override public String getType() { return type; }
    
    private final IObjectStore parent;
    private final String type;
    
    public Entity(World w, IObjectStore parent, EntityDesc desc)
            throws XMLError
    {
        super(w, desc.getProperties());
        this.parent = parent;
        this.type = desc.getType();

        addProperty(parent.getType(), parent);
    }
    

}
