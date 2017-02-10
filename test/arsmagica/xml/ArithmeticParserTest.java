/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class ArithmeticParserTest 
{
    @Test (expected = ArithmeticParser.ParseException.class)
    public void failOnEmpty() throws Exception
    {
        ArithmeticParser.eval("");
    }
}
