/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.scripts;

import arsmagica.control.MailMgr;
import arsmagica.model.objects.Entity;
import arsmagica.control.WorldMgr;
import arsmagica.model.Mail;
import arsmagica.xml.DataStore;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class RunTest 
{
    @Test
    public void runAnts() throws Exception
    {
        DataStore store = new DataStore("testdata/ants/");
        store.load();
        WorldMgr world = new WorldMgr(store);
        Entity e = world.createEntity("nest");
        
        assertEquals(0, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(2, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(7, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(16, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(30, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(50, e.get("food").asInt().getValue());        
    }
    
    @Test
    public void runAntsShort() throws Exception
    {
        DataStore store = new DataStore("testdata/ants_short/");
        store.load();
        WorldMgr world = new WorldMgr(store);
        Entity e = world.createEntity("nest");
        
        assertEquals(0, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(2, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(7, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(16, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(30, e.get("food").asInt().getValue());
        world.getTimeMgr().nextDay();
        assertEquals(50, e.get("food").asInt().getValue());        
    }
    
    @Test
    public void runStarvationStupid() throws Exception
    {
        DataStore store = new DataStore("testdata/starvation/");
        store.load();
        WorldMgr world = new WorldMgr(store);
        MailMgr mailMgr = world.getMailMgr();
        Entity e = world.createEntity("country");
        
        assertEquals(0, mailMgr.getPendingMails().size());
        
        while (mailMgr.getPendingMails().isEmpty())
            world.getTimeMgr().nextDay();
        
        assertEquals("Food is running low...", mailMgr.getPendingMails().get(0).getText());
        assertEquals(1, mailMgr.getPendingMails().size());
        
        while (mailMgr.getPendingMails().get(0).getText().equals("Food is running low..."))
        {
            Mail.Option stupidOption = mailMgr.getPendingMails().get(0).getOptions().get(1); 
            assertEquals("Do nothing. Duh!", stupidOption.getText());
            
            stupidOption.execute(world);
            world.getTimeMgr().nextDay();
            
            assertEquals(1, mailMgr.getPendingMails().size());
        }
        
        assertEquals("Everyone died. Duh.", mailMgr.getPendingMails().get(0).getText());
        mailMgr.getPendingMails().get(0).getOptions().get(0).execute(world);
        
        for (int i = 0; i < 150; i++)
        {
            world.getTimeMgr().nextDay();
            assertEquals(0, mailMgr.getPendingMails().size());
        }
    }

    @Test
    public void runStarvationSmart() throws Exception
    {
        DataStore store = new DataStore("testdata/starvation/");
        store.load();
        WorldMgr world = new WorldMgr(store);
        MailMgr mailMgr = world.getMailMgr();
        Entity e = world.createEntity("country");
        
        assertEquals(0, mailMgr.getPendingMails().size());
        
        while (mailMgr.getPendingMails().isEmpty())
            world.getTimeMgr().nextDay();
                        
        while (mailMgr.getPendingMails().isEmpty() == false)
        {
            assertEquals(1, mailMgr.getPendingMails().size());
            
            Mail newMail = mailMgr.getPendingMails().get(0);
            assertEquals("Food is running low...", newMail.getText());
            
            Mail.Option smartOption = newMail.getOptions().get(0); 
            assertEquals("Send people away", smartOption.getText());
            
            smartOption.execute(world);
            world.getTimeMgr().nextDay();
        }
                
        for (int i = 0; i < 150; i++)
        {
            assertEquals(0, mailMgr.getPendingMails().size());
            world.getTimeMgr().nextDay();
        }
    }
}
