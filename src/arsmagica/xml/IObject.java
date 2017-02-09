/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.xml;

/**
 * Represents a property of an xml-defined entity. Use getType to determine
 * the type of the property, and various cast functions to affect it.
 * 
 * @author Elscouta
 */
public interface IObject 
{
    /**
     * Returns the context of the property (generally the enclosing object). 
     * The context is where the object takes its variables from. It may be
     * different from the direct parent if this one is just a wrapper
     * object.
     * 
     * @return The context of the property
     */
    public IObjectStore getContext();
    
    /**
     * Provides access to the type of the property. Supported values are:
     * - int: This property is a plain integer (access through $prop)
     * - string: This property is a plain string (access through [prop])
     * - list: This property is a list (no direct access possible, use
     * helper tags)
     * - map: This property is a map (access through prop[key])
     * - [entity_type]: This property is an entity (access through prop.key)
     *
     * @return The type of the property
     */
    public String getType();
        
    /**
     * Returns the implementation of the property, as an integer. This can 
     * only be used if the property is of int type.
     * 
     * @return The real implementation of the property
     * @throws Mistyped The property was not an integer
     */
    default IObjectInt asInt() throws Mistyped
    {
        throw new Mistyped("int", this);
    }
    
    
    /**
     * Returns the implementation of the property, as a string. This can only 
     * be used if the property is of string type.
     * 
     * @return The real implementation of the property
     * @throws Mistyped The property was not a string
     */
    default IObjectString asString() throws Mistyped
    {
        throw new Mistyped("string", this);
    }
    
    /**
     * Returns the implementation of the property, as a list. This can only 
     * be used if the property is of list type.
     * 
     * @return The real implementation of the property
     * @throws Mistyped The property was not a list
     */
    default IObjectList asList() throws Mistyped
    {
        throw new Mistyped("string", this);
    }
    
    /**
     * Returns the implementation of the property, as an object (usually an 
     * entity, exposed only as its ability to store properties). This can only 
     * be used if the property is of an object type.
     * 
     * @return The real implementation of the property
     * @throws Mistyped The property was not an object
     */
    default PropertyContainer asObject() throws Mistyped
    {
        throw new Mistyped("object", this);
    }

    /**
     * Returns the implementation of the property, as a map. Map and object 
     * expose the same inteface, but the constraint on the properties they 
     * contain are usually different. Maps will usually allow any key of a 
     * given type while objects will have a fixed set of properties.
     * 
     * @return The value of the property
     * @throws Mistyped The property was not a map
     */
    default IObjectStore asMap() throws Mistyped
    {
        throw new Mistyped("map", this);
    }
    
    /**
     * Converts the object into a contextual string. The context of the
     * string should be the store owner of this object.
     * 
     * @return A string that includes some references to other variables.
     * @throws Mistyped The object was not convertible to a string. Only int and
     * string are convertible.
     */
    default Expression<String> toStringTemplate() throws Mistyped
    {
        throw new Mistyped("string-convertible", this);
    }
    
    /**
     * Registers a owner of an object. Any object that stores a reference to
     * another object should be registered as an owner to act upon that 
     * object destruction.
     * 
     * That function can be called on any object, but only objects that can
     * actually be destroyed should take action.
     * 
     * Care that object list must not implements these functions, because
     * they are used internally for ownership management and will generate
     * endless loop.
     * 
     * @param owner The owner to register.
     */
    default void registerOwner(IObjectOwner owner)
    {        
    }
    
    /**
     * Unregisters the owner of an object.
     * 
     * @param owner The owner to remove.
     */
    default void unregisterOwner(IObjectOwner owner)
    {
    }
    
    /**
     * Thrown when a property is called, but the type of property doesn't 
     * match the expected type.
     */
    public static class Mistyped extends XMLError
    {
        public Mistyped(String tried, IObject real)
        {
            super(String.format("Mistyped property: expected %s, got %s", tried, real.getType()));
        }
    }
    
    /**
     * Thrown when a property is used, but such a property doesn't exist.
     */
    public static class Unknown extends XMLError
    {
        public Unknown(String tried, IObjectStore store)
        {
            super(String.format("Unknown property in %s: %s", store.toString(), tried));
        }
    }
}
