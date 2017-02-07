/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import org.w3c.dom.Element;

/**
 * Abstract common class for any loader able to load a fully constructed
 * object from an XML node.
 * 
 * @param <T> The type of the constructed objects
 * 
 * @author Elscouta
 */
public abstract class XMLDirectLoader<T> extends XMLBasicLoader
{
    protected XMLDirectLoader(DataStore store)
    {
        super(store);
    }
    
    /**
     * Creates a complete object from an XML node
     * 
     * @param e An xml node
     * @return A newly created object
     * @throws XMLError Misformed XML 
     */
    public abstract T loadXML(Element e) throws XMLError;
}
