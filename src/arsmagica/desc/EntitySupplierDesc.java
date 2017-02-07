/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;

/**
 *
 * @author Elscouta
 */
public interface EntitySupplierDesc 
{
    Entity get(EntityMgr mgr);
}
