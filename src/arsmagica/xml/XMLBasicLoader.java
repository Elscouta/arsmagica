/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.objects.Entity;
import arsmagica.model.objects.IObject;
import arsmagica.model.objects.IObjectList;
import arsmagica.model.objects.PropertyContainer;
import arsmagica.model.objects.IObjectInt;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Elscouta
 */
public class XMLBasicLoader 
{        
    protected XPath xpath;
    protected DataStore store;
    
    protected XMLBasicLoader(DataStore store)
    {
        this.xpath = XPathFactory.newInstance().newXPath();
        this.store = store;
    }
    
    protected String getAttribute(Element current_node, String key)
            throws XMLError
    {
        String attr = getAttribute(current_node, key, null);
        
        if (attr == null)
            throw new XMLError(String.format(
                    "Missing expected attribute %s on %s", 
                    key, current_node.toString()
            ));
        
        return attr;
    }
    
    protected String getAttribute(Element current_node, String key, String defaultValue)
    {
        if (current_node.hasAttribute(key))
            return current_node.getAttribute(key);
        else
            return defaultValue;
    }
    
    protected String getContent(Element current_node)
            throws XMLError
    {
        if (current_node.getElementsByTagName("*").getLength() > 0)
            throw new XMLError(String.format(
                    "[line %s] : Unexpected child %s in node %s, "
                    + "expected leaf node.", 
                    current_node.getUserData("lineNumber"),
                    ((Element) current_node.getElementsByTagName("*").item(0)).getTagName(),
                    current_node.getTagName()));
        return current_node.getTextContent().trim();
    }
    
    protected <U> U getAttributeOrChild(Element current_node, String key,
            StringLoader<? extends U> attributeLoader, 
            XMLDirectLoader<? extends U> childLoader)
            throws XMLError
    {
        String attrStr = getAttribute(current_node, key, null);
        U childOpt = getChild(current_node, key, childLoader, null);
        
        if (childOpt != null && attrStr != null)
            throw new XMLError(String.format(
                    "[line %s] : Multiple definition of %s, both"
                    + "as direct child and as attribute", 
                    current_node.getUserData("lineNumber"), key));
        
        if (childOpt == null && attrStr == null)
            throw new XMLError(String.format(
                    "[line %s] : Unable to find %s, not a child"
                    + "and not an attribute", 
                    current_node.getUserData("lineNumber"), key));
        
        if (childOpt != null)
            return childOpt;
        else
            return attributeLoader.loadString(attrStr);
    }
    
    protected <U> U getAttributeOrChild(Element current_node, String key,
            XMLStringLoader<U> loader)
            throws XMLError
    {
        return getAttributeOrChild(current_node, key, loader, loader);
    }
    
    protected <U> U getChild(Element current_node, String key, 
            XMLDirectLoader<U> l)
            throws XMLError
    {
        U obj = getChild(current_node, key, l, null);

        if (obj == null)
            throw new XMLError(String.format(
                               "[line %s] : Unable to find required child %s of node %s",
                               current_node.getUserData("lineNumber"), key, current_node.getTagName()
        ));
        
        return obj;
    }
                
    protected <U> U getChild(Element current_node, String key, 
            XMLDirectLoader<U> l, U defaultValue)
            throws XMLError
    {
        try
        {
            Node n = (Node) xpath.evaluate(key, current_node, XPathConstants.NODE);
            
            if (n == null)
                return defaultValue;
                        
            assert(n.getNodeType() == Node.ELEMENT_NODE);
            
            return l.loadXML((Element) n);
        }
        catch (XPathExpressionException e)
        {
            throw new XMLError(e);
        }
    }
    
    protected <U> List<U> getChildList(Element current_node, String key, XMLDirectLoader<U> l)
            throws XMLError
    {
        try
        {
            List<U> ret = new ArrayList<>();
            NodeList nl = (NodeList) xpath.evaluate(key, current_node, XPathConstants.NODESET);
            
            for (int i = 0; i < nl.getLength(); i++)
            {
                Node n = nl.item(i);
                assert(n.getNodeType() == Node.ELEMENT_NODE);
            
                ret.add(l.loadXML((Element) n));
            }
            
            return ret;
        }
        catch (XPathExpressionException e)
        {
            throw new XMLError(e);
        }
    }
    
    protected class IObjectLoader
            extends XMLDirectLoader< Expression<IObject> >
    {
        public IObjectLoader()
        {
            super(XMLBasicLoader.this.store);
        }
        
        @Override
        public Expression<IObject> loadXML(Element e)
                throws XMLError
        {
            final String str = getContent(e);
            if (!Ref.isValidIdentifier(str))
                throw new XMLError(String.format(
                        "[line %s]: Invalid reference identifier: %s",
                        e.getUserData("lineNumber"), str));

            return context -> new Ref.Any(str, context).get();
        }
    }
    
    protected class EntityRefLoader
            extends XMLDirectLoader< Expression<Entity> >
    {
        public EntityRefLoader()
        {
            super(XMLBasicLoader.this.store);
        }
        
        @Override
        public Expression<Entity> loadXML(Element e)
                throws XMLError
        {
            final String str = getContent(e);
            if (!Ref.isValidIdentifier(str))
                throw new XMLError(String.format(
                        "[line %s]: Invalid reference identifier: %s",
                        e.getUserData("lineNumber"), str));
            return context -> new Ref.Ent(str, context).get();
        }
    }
    
    protected class IObjectIntLoader
            extends XMLStringLoader< Expression<IObjectInt> >
    {
        public IObjectIntLoader()
        {
            super(XMLBasicLoader.this.store, "Expression<IObjectInt>");
        }
        
        @Override
        public Expression<IObjectInt> loadString(String str)
                throws XMLError
        {
            try {
                Ref.checkValidIdentifier(str);
            } catch (Ref.Error ex) {
                throw new XMLError(ex);
            }

            return context -> new Ref.Int(str, context).get();
        }
    }
        
    protected class IObjectListLoader
            extends XMLStringLoader< Expression<IObjectList> >
    {
        public IObjectListLoader()
        {
            super(XMLBasicLoader.this.store, "Expression<IObjectList>");
        }
        
        @Override
        public Expression<IObjectList> loadString(String str)
                throws XMLError
        {
            try {
                Ref.checkValidIdentifier(str);
            } catch (Ref.Error ex) {
                throw new XMLError(ex);
            }
            
            return context -> new Ref.List(str, context).get();
        }
    }
    
    protected class ArithmeticLoader 
            extends XMLStringLoader< Expression<Integer> >
    {
        public ArithmeticLoader() 
        { 
            super(XMLBasicLoader.this.store, "Expression<Integer>"); 
        }
        
        @Override 
        public Expression<Integer> loadString(String str)
                throws XMLError
        {
            try {
                Expression<Double> expr = ArithmeticParser.eval(str);
                return c -> expr.resolve(c).intValue();
            } catch (ArithmeticParser.ParseException ex) {
                throw new XMLError("Unable to parse arithmetic expression", ex);
            }
        }
    }
    
    protected class ArithmeticDoubleLoader 
            extends XMLStringLoader< Expression<Double> >
    {
        public ArithmeticDoubleLoader() 
        { 
            super(XMLBasicLoader.this.store, "Expression<Double>"); 
        }
        
        @Override public Expression<Double> loadString(String str) throws XMLError
        {
            try {
                return ArithmeticParser.eval(str);
            } catch (ArithmeticParser.ParseException ex) {
                throw new XMLError("Unable to parse arithmetic expression", ex);
            }
        }
    }
    
    protected class ContentLoader extends XMLStringLoader<String>
    {
        public ContentLoader()
        {
            super(XMLBasicLoader.this.store, "String");
        }
        
        @Override public String loadString(String str) throws XMLError
        {
            return str;
        }
    }

    protected class Failure extends XMLError
    {
        Failure(Element e, String classname, XMLError error)
        {
            super(String.format("[line %s] : Failed to load node %s into a %s", 
                  e.getUserData("lineNumber"), e.getTagName(), classname), 
                  error);
        }
    }
}
