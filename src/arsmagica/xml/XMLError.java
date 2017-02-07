/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * An exception raised when the XML is ill-formed.
 * 
 * @author Elscouta
 */
public class XMLError extends Exception
{
    public XMLError() { super(); }
    public XMLError(String msg) { super(msg); }
    public XMLError(Exception e) { super(e); }
    public XMLError(String msg, Exception e) { super(msg, e); }
}
