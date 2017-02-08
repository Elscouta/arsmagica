/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.desc.IObjectIntDesc;
import java.util.List;

/**
 * Represents a property of type int.
 * @author Elscouta
 */
public final class IObjectInt implements IObject
{
    @Override public IObjectStore getContext() { return context; }
    @Override public String getType() { return "int"; }
    @Override public IObjectInt asInt() { return this; }
    
    public final static class Relation
    {
        final Ref<IObjectInt> related;
        final int correlation;
        
        public Relation(Ref<IObjectInt> related, int correlation)
        {
            this.related = related;
            this.correlation = correlation;
        }
    }
    
    private final IObjectStore context;
    private final IObjectIntDesc desc;
    private int value;
    private final List<Relation> relations;

    public IObjectInt(IObjectStore c, IObjectIntDesc desc) 
            throws XMLError
    {
        this.context = c;
        this.desc = desc;
        this.value = desc.getInitialValue(c);
        this.relations = desc.getRelations(c);
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void applyDelta(int delta)
    {
        value = value + delta;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(value);
    }
    
    @Override
    public Expression<String> toStringTemplate()
    {
        return c -> String.valueOf(value);
    }
}
