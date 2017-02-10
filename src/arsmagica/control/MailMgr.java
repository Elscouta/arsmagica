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
import javax.swing.SwingUtilities;

/**
 * The class that manages the display of all popup dialogs that are shown to 
 * the user.
 * 
 * This class is synchronized and thread safe.
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
     * Creates a new mail. If you intend to contiue the creation of the mail
     * after calling that function, you should synchronize on the mailmgr for
     * as long as you intend to modify the mail.
     * 
     * @param blocking Whether this mail should prevent moving to the next
     * day
     * @param text The text of the mail
     * @return A new mail
     */
    public synchronized Mail createMail(boolean blocking, String text)
    {
        Mail mail = new Mail(this, text);
        
        pendingMails.add(mail);
        if (blocking) 
            blockingMails.add(mail);
        
        notifyListeners();
        
        return mail;
    }
    
    /**
     * Removes a given mail from the list of pending mails.
     * 
     * @param mail The mail to remove
     */
    public synchronized void destroyMail(Mail mail)
    {
        pendingMails.remove(mail);
        blockingMails.remove(mail);
        
        notifyListeners();
    }
    
    /**
     * Returns the list of pending mails. The list should not be changed.
     * 
     * @return A ready-only view of the pending mails.
     */
    public synchronized List<Mail> getPendingMails()
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
    public synchronized void closeDay() 
            throws TimeMgr.NotReady
    {
        if (!blockingMails.isEmpty())
            throw new TimeMgr.NotReady("MailMgr: Blocked by unresolved mail.");
    }
    
    /**
     * Returns whether the manager is ready to switch to the next day (aka
     * no blocking mails are left)
     */
    @Override 
    public synchronized boolean isReady()
    {
        return blockingMails.isEmpty();
    }
    
    /**
     * Interface to be used by objects that wish to be informed about changes
     * in the mail manager. Messages sent through that interface are
     * forced into the event dispatch thread.
     */
    @FunctionalInterface
    public interface Listener 
    {
        /**
         * The content of the mail box changed.
         */
        public void onChange();
    }
    
    /**
     * Use this to register a listener to the mail manager.
     * 
     * @param l The listener to add.
     */
    public synchronized void addListener(Listener l)
    {
        listeners.add(l);
    }
    
    private final List<Mail> pendingMails = new ArrayList<>();
    private final List<Mail> blockingMails = new ArrayList<>();
    private final List<Listener> listeners = new ArrayList<>();
    
    private void notifyListeners()
    {
        SwingUtilities.invokeLater(() -> {
            synchronized (MailMgr.this) {
                for (Listener l : listeners)
                    l.onChange();
            }
        });
    }
}
