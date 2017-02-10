/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.function.Supplier;
import org.w3c.dom.Element;

/**
 * An interface for factories able to construct objects from xml files. This 
 * class includes various useful functions to help implementing classes.
 * 
 * @param <T> the class type that is loaded
 * @author Elscouta
 */
public abstract class XMLLoader<T> 
        extends XMLDirectLoader<T>
        implements XMLSubLoader<T>
{
    private final Supplier<T> factory;
    
    protected XMLLoader(DataStore store, Supplier<T> factory)
    {
        super(store);
        this.factory = factory;
    }
    
    @Override 
    public T loadXML(Element e) 
            throws XMLError
    {
        T obj = factory.get();
        
        try
        {
            fillObjectFromXML(obj, e);
            return obj;
        }
        catch (XMLError exception)
        {
            throw new Failure(e, obj.getClass().getName(), exception);
        }
    }
}
