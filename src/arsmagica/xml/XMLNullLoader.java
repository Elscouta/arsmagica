/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.function.Supplier;
import org.w3c.dom.Element;

/**
 *
 * @author Elscouta
 */
public class XMLNullLoader<T> extends XMLLoader<T>
{
    public XMLNullLoader(Supplier<T> factory)
    {
        super(null, factory);
    }
    
    @Override public void fillObjectFromXML(T obj, Element e)
    {        
    }
}
