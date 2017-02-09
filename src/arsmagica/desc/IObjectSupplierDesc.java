/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.Context;
import arsmagica.xml.IObject;
import arsmagica.xml.PropertyContainer;
import arsmagica.xml.Ref;

/**
 * Describes a method able to return an object given a context.
 * 
 * @author Elscouta
 */
public interface IObjectSupplierDesc 
{
    /**
     * Return an object, could be new or existing, depending of the
     * implementation.
     * 
     * @param w The world object
     * @param parent The object that will store this new element, and is the
     * general "parent" if it is being created.
     * @param context A context in which free variables must be searched.
     * @return An object that can be stored in a XML-defined container.
     * @throws Ref.Error The resolution with the context was unsuccessful.
     */ 
    IObject get(World w, IObject parent, Context context) 
            throws Ref.Error;
}
