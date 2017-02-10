/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

import arsmagica.model.objects.Context;

/**
 *
 * @author Elscouta
 */
public class StringParser 
{
    public static Expression<String> eval(final String str) 
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
                if (ch == charToEat) 
                {
                    nextChar();
                    return true;
                }
            
                return false;
            }
            
            boolean eatWhitespace()
            {
                boolean r = false;
                while (Character.isWhitespace(ch))
                {
                    nextChar();
                    r = true;
                }
                
                return r;
            }

            Expression<String> parse() 
            {
                nextChar();
            
                Expression<String> x = c -> "";
                boolean whitespace = false;
                
                while (Character.isWhitespace(ch))
                    nextChar();
                
                while (pos < str.length())
                {
                    if (eat('$'))
                    {
                        int startPos = pos;
                        
                        while (ch >= 'a' && ch <= 'z' ||
                               ch >= 'A' && ch <= 'Z' ||
                               ch >= '0' && ch <= '9' ||
                               ch == '[' || ch == ']' ||
                               ch == '.' || ch == '_')
                            nextChar();
                        
                        final String var = str.substring(startPos, this.pos);
                        final Expression<String> x1 = x;
                        final String extraWhitespace = whitespace ? " " : "";                        
                        
                        x = (Context c) ->  x1.resolve(c).concat(extraWhitespace).concat(
                                (new Ref.Any(var, c)).get().toString()
                        );
                    }
                    else if (eatWhitespace())
                    {
                        whitespace = true;
                    }                        
                    else
                    {
                        int startPos = pos;
                        while (ch != '$' && ch != -1 && 
                               !Character.isWhitespace(ch))
                            nextChar();
                        
                        final String text = str.substring(startPos, this.pos);
                        final Expression<String> x1 = x;
                        final String extraWhitespace = whitespace ? " " : "";
                                                
                        x = (Context c) ->  x1.resolve(c).concat(extraWhitespace).concat(text);
                    }
                }
                                
                return x;
            }
        }.parse();                
    }
}
