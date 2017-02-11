/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model.objects;

import arsmagica.desc.IObjectIntDesc;
import arsmagica.xml.Expression;
import arsmagica.xml.Ref;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a property of type int.
 * @author Elscouta
 */
public final class IObjectInt implements IObject
{
    @Override public Context getContext() { return context; }
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
    
    private final Context context;
    private int value;
    private final List<Relation> relations;

    public IObjectInt(Context c, IObjectIntDesc desc) 
            throws Ref.Error
    {
        this.context = c;
        this.value = desc.getInitialValue(c);
        this.relations = desc.getRelations(c);
    }
    
    public IObjectInt(Context c, int value)
    {
        this.context = c;
        this.value = value;
        this.relations = new ArrayList<>();
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
