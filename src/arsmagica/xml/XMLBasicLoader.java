/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

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
    {
        return current_node.getAttribute(key);
    }
    
    protected String getContent(Element current_node)
    {
        return current_node.getTextContent();
    }

    protected <U> U getChild(Element current_node, String key, XMLDirectLoader<U> l)
            throws XMLError
    {
        try
        {
            Node n = (Node) xpath.evaluate(key, current_node, XPathConstants.NODE);
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
                Node n = (Node) xpath.evaluate(key, current_node, XPathConstants.NODE);
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
    
    protected class ArithmeticLoader extends XMLDirectLoader< Expression<Integer> >
    {
        public ArithmeticLoader() 
        { 
            super(XMLBasicLoader.this.store); 
        }
        
        @Override public Expression<Integer> loadXML(Element e) throws XMLError
        {
            return ArithmeticParser.eval(getContent(e));
        }
    }
    
    protected class ContentLoader extends XMLDirectLoader<String>
    {
        public ContentLoader()
        {
            super(XMLBasicLoader.this.store);
        }
        
        @Override public String loadXML(Element e) throws XMLError
        {
            return getContent(e);
        }
    }
}
