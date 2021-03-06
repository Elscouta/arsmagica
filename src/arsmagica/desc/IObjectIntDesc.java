/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.control.WorldMgr;
import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.model.objects.IObjectInt;
import arsmagica.xml.Expression;
import arsmagica.model.objects.IObject;
import arsmagica.xml.MethodIntLoader;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import arsmagica.model.objects.PropertyContext;

/**
 *
 * @author Elscouta
 */
public class IObjectIntDesc extends IObjectDesc
{
    private static final class RelationDesc
    {
        private String relatedPath;
        private Expression<Integer> correlation;
        
        public IObjectInt.Relation resolve(Context c)
                throws Ref.Error
        {
            Ref<IObjectInt> rProperty = new Ref.Int(relatedPath, c);
            int rCorrelation = correlation.resolve(c);
            
            return new IObjectInt.Relation(rProperty, rCorrelation);
        }
        
        static final class Loader extends XMLLoader<RelationDesc>
        {
            Loader(DataStore store)
            {
                super(store, () -> new RelationDesc());
            }

            @Override
            public void fillObjectFromXML(RelationDesc obj, Element e)
                    throws XMLError
            {
                obj.relatedPath = getAttribute(e, "to");
                obj.correlation = getChild(e, "correlation", new ArithmeticLoader());
            }
        }
    }
    
    private Expression<Integer> initializer;
    private List<RelationDesc> relations;
    
    @Override
    public IObjectIntDesc overwrite(String key, IObjectDesc other)
            throws Mistyped
    {
        if (!(other instanceof IObjectIntDesc))
            throw new Mistyped(String.format(
                    "Attempting overwrite of int with %s",
                    other.getType()));
        
        IObjectIntDesc otherObj = (IObjectIntDesc) other;
        IObjectIntDesc retObj = new IObjectIntDesc();

        retObj.initializer = (Context c) -> {
            int iV = initializer.resolve(c);
            IObjectInt objV = new IObjectInt(c, iV);
            Context nc = Context.createWrapper(key, objV, c);
            return otherObj.initializer.resolve(nc);            
        };
        
        retObj.relations = otherObj.relations;
        
        return retObj;
    }
    
    public int getInitialValue(Context c)
            throws Ref.Error
    {
        return initializer.resolve(c);
    }
    
    public List<IObjectInt.Relation> getRelations(Context c)
            throws Ref.Error
    {
        List<IObjectInt.Relation> ret = new ArrayList<>();
        for (RelationDesc rd : relations)
            ret.add(rd.resolve(c));
        
        return ret;
    }
    
    @Override
    public String getType()
    {
        return "int";
    }
    
    @Override
    public IObjectInt create(WorldMgr w, IObject parent, Context context)
            throws Ref.Error
    {
        return new IObjectInt(context, this);
    }
    
    public static final class Loader extends XMLLoader<IObjectIntDesc>
    {
        public Loader(DataStore store)
        {
            super(store, () -> new IObjectIntDesc());
        }
        
        @Override 
        public void fillObjectFromXML(IObjectIntDesc obj, Element e)
                throws XMLError
        {
            obj.initializer = getAttributeOrChild(e, "init", 
                                       new MethodIntLoader.Const.Loader(store),
                                       new MethodIntLoader(store));
            obj.relations = getChildList(e, "related", 
                                         new RelationDesc.Loader(store));
        }
    }
}
