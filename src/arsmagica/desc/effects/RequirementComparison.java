/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.desc.effects;

import arsmagica.model.objects.Context;
import arsmagica.xml.DataStore;
import arsmagica.xml.Expression;
import arsmagica.xml.Ref;
import arsmagica.xml.XMLError;
import arsmagica.xml.XMLLoader;
import arsmagica.xml.XMLStringLoader;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.w3c.dom.Element;

/**
 * A requirement for any arithmetic comparison between two integers (or
 * doubles).
 * 
 * @author Elscouta
 */
public class RequirementComparison extends Requirement
{
    private Expression<Double> left;
    private Expression<Double> right;
    private BiPredicate<Double, Double> comparator;
    
    @Override
    public boolean test(Context c) 
            throws Ref.Error
    {
        return comparator.test(left.resolve(c), right.resolve(c));
    }
    
    public static class LoaderXML extends XMLLoader<RequirementComparison>
    {
        private final BiPredicate<Double, Double> comparator;
        
        public LoaderXML(DataStore store, BiPredicate<Double, Double> comparator)
        {
            super(store, () -> new RequirementComparison());
            this.comparator = comparator;
        }
        
        @Override
        public void fillObjectFromXML(RequirementComparison obj, Element e)
                throws XMLError
        {
            obj.comparator = comparator;
            obj.left = getAttributeOrChild(e, "left", new ArithmeticDoubleLoader());
            obj.right = getAttributeOrChild(e, "right", new ArithmeticDoubleLoader());
        }
    }
    
    public static class LoaderString 
            extends XMLStringLoader<RequirementComparison>
    {
        static final Pattern BINARY_PREDICATE_REGEX = 
                Pattern.compile("([^=]*)(==|=gt=|=gte=|=lt=|=lte=)([^=]*)");
        
        public LoaderString(DataStore store)
        {
            super(store, "RequirementComparison");
        }
        
        private BiPredicate<Double, Double> getPredicate(String str)
                throws XMLError
        {
            switch (str)
            {
                case "==":  return (a, b) -> a.equals(b);
                case "=gt=":   return (a, b) -> a > b;
                case "=lt=":   return (a, b) -> a < b;
                case "=gte=":  return (a, b) -> a >= b;
                case "=lte=":  return (a, b) -> a <= b;
                default:    throw new XMLError("Unknown operator.");
            }
        }
        
        @Override
        public RequirementComparison loadString(String str)
                throws XMLError
        {
            Matcher m = BINARY_PREDICATE_REGEX.matcher(str);
            if (m.matches())
            {
                RequirementComparison obj = new RequirementComparison();
                obj.left = (new ArithmeticDoubleLoader()).loadString(m.group(1));
                obj.right = (new ArithmeticDoubleLoader()).loadString(m.group(3));
                obj.comparator = getPredicate(m.group(2));
                return obj;
            }
            else
            {
                throw new XMLError("Not a valid arithmetic predicate");
            }            
        }
    }
}
