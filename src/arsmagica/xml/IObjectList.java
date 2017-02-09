/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a list of properties, sharing the same type. This is
 * actually a set (no duplicated allowed), but i chose the name list to
 * confuse people.
 * 
 * Muahaha.
 * 
 * @author Elscouta
 */
public class IObjectList implements IObject, IObjectOwner, Iterable<IObject>
{
    @Override public Context getContext() { return context; }
    @Override public String getType() { return "list"; }
    @Override public IObjectList asList() { return this; }
    
    private final Context context;
    private final List<IObject> list;
    

    public IObjectList(Context context)
    {
        this.context = context;
        this.list = new ArrayList<>();
    }
    
    /**
     * Adds an element to the list. If the element is already present, 
     * nothing is done (the element is not duplicated).
     * 
     * @param element The element that must be added to the list
     */
    public void addElement(IObject element)
    {
        if (list.contains(element))
            return;
        
        element.registerOwner(this);
        list.add(element);
    }
    
    /**
     * Removes an element from the list. The element must be already present.
     * 
     * @param removed The removed object. 
     */
    public void removeElement(IObject removed)
    {
        if (!list.contains(removed))
            throw new RuntimeException("Removed object is absent.");
        
        removed.unregisterOwner(this);
        list.remove(removed);
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
    
    @Override
    public String toString()
    {
        return String.format("List : size = %d", list.size());
    }
    
    @Override
    public void notifyMemberDestroyed(IObject destroyed)
    {
        removeElement(destroyed);
    }
    
    IObjectList removeAll()
    {
        IObjectList r = new IObjectList(context);
        
        for (IObject o : list)
        {
            o.unregisterOwner(this);
            r.addElement(o);
        }
            
        list.clear();
        
        return r;        
    }

    @Override
    public Iterator<IObject> iterator() 
    {
        return new Iterator<IObject>() 
        {
            private int i = 0;
            
            @Override
            public boolean hasNext() 
            {
                return i < list.size();
            }

            @Override
            public IObject next() 
            {
                return list.get(i++);
            }            
        };
    }
}
