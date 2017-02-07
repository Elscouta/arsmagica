/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import org.w3c.dom.Element;

/**
 * Interface implemented by loaders that are able to fill an existing object
 * instead of creating from scratch.
 * 
 * @param <T> The type of supported objects
 * 
 * @author Elscouta
 */
public interface XMLSubLoader<T>
{
    public void fillObjectFromXML(T object, Element e)
            throws XMLError; 
}
