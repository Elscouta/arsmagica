/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * Interface for objects that are able to load from a simple string.
 * 
 * @param <T> The object that are generated.
 * 
 * @author Elscouta
 */
public interface StringLoader<T>
{
    T loadString(String str) throws XMLError;
}
