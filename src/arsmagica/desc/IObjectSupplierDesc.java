/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.World;
import arsmagica.xml.IObject;
import arsmagica.xml.IObjectStore;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public interface IObjectSupplierDesc 
{
    IObject get(World w, IObjectStore parent) 
            throws Ref.Error;
}
