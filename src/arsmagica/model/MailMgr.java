/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

/**
 * The class that manages the display of all popup dialogs that are shown to 
 * the user.
 * 
 * @author Elscouta
 */
public class MailMgr implements TimeMgr.Listener
{
    boolean blocked = false;
    
    public MailMgr(World w) {}
    
    public Mail createMail(boolean blocking, String text)
    {
        if (blocking) blocked = blocking;
        
        return new Mail(text);
    }
    
    @Override
    public void closeDay() 
            throws TimeMgr.NotReady
    {
        if (blocked)
            throw new TimeMgr.NotReady("MailMgr: Blocked by unresolved mail.");
    }
}
