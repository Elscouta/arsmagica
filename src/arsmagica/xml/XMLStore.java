/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to hold a set of Identifiable objects, with basic loading / query
 * features.
 * 
 * @param <T> The class of the objects that will be stored inside the store.
 * @author Elscouta
 */
public class XMLStore<T extends Identifiable>
{
    XMLLoader<T> loader;
    Map<String, T> store;
    
    public XMLStore(XMLLoader<T> loader)
    {
        this.loader = loader;
        this.store = new HashMap<>();
    }
    
    public final void load(String pathname, String nodename, DataStore mainstore) 
            throws XMLError
    {
        XMLFileLoader<T> fLoader = 
                new XMLFileLoader(mainstore, nodename, loader);

        for (T obj : fLoader.loadXML(pathname))
        {
            store.put(obj.getIdentifier(), obj);
            
            System.out.printf("loaded %s: %s - %s\n", 
                    nodename, obj.getIdentifier(), obj.toString());
        }
    }
    
    public T get(String identifier)
    {
        assert(identifier != null && !identifier.equals(""));
        
        T elem = store.get(identifier);
        
        if (elem == null)
            System.out.printf("WARNING: Store miss [%s]\n", identifier);
        
        return elem;
    }

}
