/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc;

import arsmagica.model.Entity;
import arsmagica.model.EntityMgr;
import arsmagica.model.World;
import arsmagica.xml.DataStore;
import arsmagica.xml.IObjectInt;
import arsmagica.xml.Expression;
import arsmagica.xml.MethodIntLoader;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import arsmagica.xml.IObjectStore;

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
        
        public IObjectInt.Relation resolve(IObjectStore c)
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
    
    public int getInitialValue(IObjectStore c)
            throws Ref.Error
    {
        return initializer.resolve(c);
    }
    
    public List<IObjectInt.Relation> getRelations(IObjectStore c)
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
    public IObjectInt create(World w, IObjectStore context)
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
            obj.initializer = getChild(e, "init", 
                                       new MethodIntLoader(store));
            obj.relations = getChildList(e, "related", 
                                         new RelationDesc.Loader(store));
        }
    }
}
