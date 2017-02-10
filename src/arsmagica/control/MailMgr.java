/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.control;

import arsmagica.model.Mail;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class that manages the display of all popup dialogs that are shown to 
 * the user.
 * 
 * @author Elscouta
 */
public class MailMgr implements TimeMgr.Listener
{
    /** 
     * Builds a new mail mgr
     * 
     * @param w A link to the world mgr.
     */
    public MailMgr(WorldMgr w) 
    {
        
    }
    
    /**
     * Creates a new mail
     * 
     * @param blocking Whether this mail should prevent moving to the next
     * day
     * @param text The text of the mail
     * @return A new mail
     */
    public Mail createMail(boolean blocking, String text)
    {
        Mail mail = new Mail(this, text);
        
        pendingMails.add(mail);
        if (blocking) 
            blockingMails.add(mail);
        
        return mail;
    }
    
    /**
     * Removes a given mail from the list of pending mails.
     * 
     * @param mail The mail to remove
     */
    public void destroyMail(Mail mail)
    {
        pendingMails.remove(mail);
        blockingMails.remove(mail);
    }
    
    /**
     * Returns the list of pending mails. The list should not be changed.
     * 
     * @return A ready-only view of the pending mails.
     */
    public List<Mail> getPendingMails()
    {
        return Collections.unmodifiableList(pendingMails);
    }
    
    /**
     * Called when the day is about to end. Checks whether all blocking mails 
     * have been handled.
     * 
     * @throws TimeMgr.NotReady Some pending mails still required action.
     */
    @Override
    public void closeDay() 
            throws TimeMgr.NotReady
    {
        if (!blockingMails.isEmpty())
            throw new TimeMgr.NotReady("MailMgr: Blocked by unresolved mail.");
    }
    
    private final List<Mail> pendingMails = new ArrayList<>();
    private final List<Mail> blockingMails = new ArrayList<>();        
}
