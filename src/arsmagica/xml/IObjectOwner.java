/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * An interface for objects that can keep references to other
 * objects. Used to send notifications.
 * 
 * @author Elscouta
 */
public interface IObjectOwner
{
    /**
     * Informs the object that one of its member got destroyed. What happens
     * then depends of the receiving class:
     * 
     * A property container: the enclosing object is *destroyed*
     * A mutable object wrapper: the object is set to null
     * A list: the object is removed from the list
     * 
     * @param destroyed The destroyed object. Note that this object is only
     * provided for adress reference and should not be used anymore.
     */
    void notifyMemberDestroyed(IObject destroyed);
}
