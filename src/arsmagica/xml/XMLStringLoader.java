/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import org.w3c.dom.Element;

/**
 * An adapter for objects that can be generated from a simple string, so that
 * they also provide the XMLDirectLoader interface.
 * 
 * @param <T> the class being loaded
 * 
 * @author Elscouta
 */
public abstract class XMLStringLoader<T>
        extends XMLDirectLoader<T>
        implements StringLoader<T>
{
    String className;
    
    public XMLStringLoader(DataStore store, String className)
    {
        super(store);
        this.className = className;
    }
    
    @Override
    public T loadXML(Element e)
            throws XMLError
    {
        try
        {
            return loadString(getContent(e));
        }
        catch (XMLError exception)
        {
            throw new Failure(e, className, exception);
        }
    }
}
