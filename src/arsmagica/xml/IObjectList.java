/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.IObjectListDesc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a list of properties, sharing the same type.
 * 
 * @author Elscouta
 */
public class IObjectList implements IObject
{
    @Override public IObjectStore getContext() { return context; }
    @Override public String getType() { return "list"; }
    @Override public IObjectList asList() { return this; }
    
    private final IObjectStore context;
    private final String elementType;
    private final List<IObject> list;
    
    public IObjectList(IObjectStore context, String elementType)
    {
        this.context = context;
        this.elementType = elementType;
        this.list = new ArrayList<>();
    }
    
    public void addElement(IObject element)
            throws XMLError
    {
        if (element.getType().equals(elementType) == false)
            throw new XMLError(String.format(
                    "Mismatched types when adding to list: %s, expected %s",
                    element.getType(), elementType
            ));
        
        list.add(element);
    }
    
    public IObject getRandom()
    {
        int size = list.size();
        int r = ThreadLocalRandom.current().nextInt(0, size);
        
        return list.get(r);
    }
    
    public int getSize()
    {
        return list.size();
    }
}
