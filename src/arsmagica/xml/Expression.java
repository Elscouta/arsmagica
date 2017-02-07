/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * An expression is any xml content with no markup that may contain references
 * to properties.
 * 
 * It can be resolved by passing a context to it, in which case, assuming all
 * open variables are properly present (and with the correct type), it will
 * yield the promised object.
 * 
 * @param <T> The type that will be returned on resolution.
 * 
 * @author Elscouta
 */
@FunctionalInterface
public interface Expression<T>
{
    /**
     * Resolves the expresion.
     * 
     * @param c The context, that should contain all variables that were
     * left open in the expression.
     * 
     * @return The promised object
     * @throws XMLError A variable was not found, or was of the wrong type.
     */
    public T resolve(IObjectStore c) throws XMLError;
}
