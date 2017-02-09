/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 *
 * @author Boann @ StackOverflow
 */
public class ArithmeticParser 
{
    public static Expression<Double> eval(final String str) 
    {
        return new Object() 
        {
            int pos = -1, ch;

            void nextChar() 
            {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) 
            {
                while (ch == ' ') nextChar();
                if (ch == charToEat) 
                {
                    nextChar();
                    return true;
                }
            
                return false;
            }

            Expression<Double> parse() 
            {
                nextChar();
            
                Expression<Double> x = parseExpression();
                
                if (pos < str.length()) 
                    throw new RuntimeException("Unexpected: " + (char)ch);
                
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            Expression<Double> parseExpression() 
            {
                Expression<Double> x = parseTerm();
                while (true)
                {
                    if      (eat('+'))
                    {
                        Expression<Double> x1 = x;
                        Expression<Double> x2 = parseTerm();
                        x = c -> (x1.resolve(c) + x2.resolve(c));
                    }
                    else if (eat('-'))
                    {
                        Expression<Double> x1 = x;
                        Expression<Double> x2 = parseTerm();
                        x = c -> (x1.resolve(c) + x2.resolve(c));
                    } 
                    else 
                    {
                        return x;
                    }
                }
            }

            Expression<Double> parseTerm() 
            {
                Expression<Double> x = parseFactor();

                while (true)
                {
                    if      (eat('*'))
                    {
                        Expression<Double> x1 = x;
                        Expression<Double> x2 = parseFactor();
                        x = c -> x1.resolve(c) * x2.resolve(c);
                    } 
                    else if (eat('/')) 
                    {
                        Expression<Double> x1 = x;
                        Expression<Double> x2 = parseFactor();
                        x = c -> x1.resolve(c) / x2.resolve(c);
                    } // division
                    else 
                        return x;
                }
            }

            Expression<Double> parseFactor() 
            {
                if (eat('+')) 
                    return parseFactor(); // unary plus
                if (eat('-'))
                {
                    Expression<Double> e = parseFactor();
                    return c -> - e.resolve(c); // unary minus
                }
                    
                Expression<Double> x;
                
                int startPos = this.pos;
                if (eat('(')) 
                { // parentheses
                    x = parseExpression();
                    if (!eat(')'))
                        throw new RuntimeException("Failed to find closing parenthesis in expression.");
                } 
                else if ((ch >= '0' && ch <= '9') || ch == '.') 
                { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') 
                        nextChar();
                    
                    final double fixedValue = Double.parseDouble(str.substring(startPos, this.pos));
                
                    x = (c -> fixedValue);
                }
                else if (eat('$'))
                {
                    while (ch >= 'a' && ch <= 'z' ||
                           ch >= 'A' && ch <= 'Z' ||
                           ch == '_')
                        nextChar();

                    while (ch >= 'a' && ch <= 'z' ||
                           ch >= 'A' && ch <= 'Z' ||
                           ch >= '0' && ch <= '9' ||
                           ch == '[' || ch == ']' ||
                           ch == '.' || ch == '_')
                        nextChar();
                    
                    final String var = str.substring(startPos+1, this.pos);
                    
                    x = (IObjectStore c) -> (double) (new Ref.Int(var, c)).get().getValue();                    
                }   
                else if (ch >= 'a' && ch <= 'z') 
                { // functions
                    while (ch >= 'a' && ch <= 'z') 
                        nextChar();

                    String func = str.substring(startPos, this.pos);
                    Expression<Double> x1 = parseFactor();
                    
                    if (func.equals("sqrt")) 
                        x = c -> Math.sqrt(x1.resolve(c));
                    else if (func.equals("sin")) 
                        x = c -> Math.sin(Math.toRadians(x1.resolve(c)));
                    else if (func.equals("cos")) 
                        x = c -> Math.cos(Math.toRadians(x1.resolve(c)));
                    else if (func.equals("tan")) 
                        x = c -> Math.tan(Math.toRadians(x1.resolve(c)));
                    else 
                        throw new RuntimeException("Unknown function: " + func);
                } 
                else 
                {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^'))
                {
                    Expression<Double> x1 = x;
                    Expression<Double> x2 = parseFactor();
                    x = c -> Math.pow(x1.resolve(c), x2.resolve(c)); // exponentiation
                }

                return x;
        }
    }.parse();
}
}
