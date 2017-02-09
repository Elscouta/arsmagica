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
public interface IObjectStore extends IObjectOwner
{
    public IObject get(String key) throws IObject.Unknown;
}
