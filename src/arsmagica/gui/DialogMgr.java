/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.gui;

import arsmagica.model.World;

/**
 * The class that manages the display of all popup dialogs that are shown to 
 * the user.
 * 
 * @author Elscouta
 */
public class DialogMgr 
{
    public DialogMgr(World w) {}
    
    public Dialog createDialog(boolean blocking, String text)
    {
        return new Dialog(text);
    }
}
