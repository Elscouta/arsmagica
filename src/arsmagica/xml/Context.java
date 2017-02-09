/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 *
 * @author Elscouta
 */
public interface Context 
{
    public IObject get(String key) throws Context.Unknown;
            
    /**
     * Thrown when a property is used, but such a property doesn't exist.
     */
    public static class Unknown extends Exception
    {
        public Unknown(String tried, PropertyContext store)
        {
            super(String.format("Unknown property in %s: %s", store.toString(), tried));
        }
    }

}
