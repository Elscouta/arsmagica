/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A loader that interprets the node content as a file path, and loads
 * that file before passing it to the wrapped loader. The root of the loaded
 * file should only contain nodes that are directly loadable.
 * 
 * @param <T> The type of objects to load
 * 
 * @author Elscouta
 */
public class XMLFileLoader<T> extends XMLDirectLoader<List<T>>
{
    private final String tagname;
    private final XMLDirectLoader<T> subLoader;
    
    public XMLFileLoader(DataStore store, 
                         String tagname, XMLDirectLoader<T> loader)
    {
        super(store);
        this.tagname = tagname;
        this.subLoader = loader;
    }
    
    @Override
    public List<T> loadXML(Element e) throws XMLError
    {
        String filepath = getContent(e);
     
        return loadXML(filepath);
    }
    
    public List<T> loadXML(String filepath) throws XMLError
    {
        try {
            List<T> retList = new ArrayList<>();
            
            File fDesc = new File(store.getGamedataPath() + filepath);
            Document xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fDesc);
            xmlDoc.getDocumentElement().normalize();
                
            NodeList nodeList = xmlDoc.getElementsByTagName(tagname);
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Element e = (Element) nodeList.item(i);

                T obj = subLoader.loadXML(e);
                retList.add(obj);
            }
            
            return retList;
        }
        catch (IOException e) {
            throw new XMLError(e);
        }
        catch (ParserConfigurationException e) {
            throw new XMLError("Could not configure XML parser", e);
        }
        catch (SAXException e) {
            throw new XMLError("Could not parse XML File", e);
        }        

    }
}
