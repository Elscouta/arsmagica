/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.World;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class SetupTest 
{
    @Test
    public void setupWorld() 
    {
        DataStore store = new DataStore("testdata/units/");
        World world = new World(store);   
    }
}
