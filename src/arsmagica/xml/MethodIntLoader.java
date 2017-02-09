/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import java.util.concurrent.ThreadLocalRandom;
import org.w3c.dom.Element;

/**
 * Provides various way to create arirthmetic expressions, when a normal
 * expression isn't enough. Supported methods are currently:
 * - const: generate a const expression (can actually be any arithmetic
 * expression)
 * - random: generates an integer that is randomly included between
 * two arithmetic expressions.
 * 
 * @author Elscouta
 */
public class MethodIntLoader extends XMLDirectLoader< Expression<Integer> >
{
    public MethodIntLoader(DataStore store)
    {
        super(store);
    }

    /**
     * Returns the specialized loader, based on the method.
     * @param method supported methods are: const, random
     * @return A loader for the specified method
     * @throws XMLError Malformed XML.
     */
    private XMLDirectLoader<? extends Expression<Integer>> getLoader(String method)
            throws XMLError
    {
        switch (method)
        {
            case "const":  return new Const.Loader(store);
            case "random": return new Random.Loader(store);
            case "get_random": return new GetRandom.IntLoader(store);
            default: 
                throw new XMLError(String.format("Unknown method: %s", method));
        }
    }
    
    @Override
    public Expression<Integer> loadXML(Element e) 
            throws XMLError 
    {
        String method = getAttribute(e, "method");
        return getLoader(method).loadXML(e);
    }

    /**
     *
     * @author Elscouta
     */
    public static class Const implements Expression<Integer> 
    {
        private Expression<Integer> v;

        @Override
        public Integer resolve(Context c) throws Ref.Error 
        {
            return v.resolve(c);
        }

        public static class Loader extends XMLLoader<Const> 
        {
            public Loader(DataStore s) 
            {
                super(s, () -> new Const());
            }

            @Override
            public void fillObjectFromXML(Const obj, Element e) 
                    throws XMLError 
            {
                obj.v = getChild(e, "value", new ArithmeticLoader());
            }
        }
    }

    /**
     *
     * @author Elscouta
     */
    public static class Random implements Expression<Integer> {

        private Expression<Integer> min;
        private Expression<Integer> max;

        @Override
        public Integer resolve(Context c) 
                throws Ref.Error 
        {
            int iMin = min.resolve(c);
            int iMax = max.resolve(c);
            return ThreadLocalRandom.current().nextInt(iMin, iMax + 1);
        }

        public static class Loader extends XMLLoader<Random> {

            public Loader(DataStore store) {
                super(store, () -> new Random());
            }

            @Override
            public void fillObjectFromXML(Random obj, Element e) throws XMLError {
                obj.min = getChild(e, "min", new ArithmeticLoader());
                obj.max = getChild(e, "max", new ArithmeticLoader());
            }
        }
    }
}
