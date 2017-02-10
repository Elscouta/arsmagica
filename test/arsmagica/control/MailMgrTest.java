/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.control;

import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.EffectList;
import arsmagica.model.Mail;
import arsmagica.model.objects.PropertyContext;
import arsmagica.xml.DataStore;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class MailMgrTest 
{
    private WorldMgr world;
    private MailMgr mailMgr;
    private Effect emptyEffect;
    private Effect successEffect;
    
    private static class SuccessException extends RuntimeException {}
    
    @Before
    public void setUp()
    {
        DataStore store = new DataStore("");
        world = new WorldMgr(store);
        mailMgr = world.getMailMgr();
        emptyEffect = new EffectList(new ArrayList<>());
        successEffect = (w, c) -> { throw new SuccessException(); };
    }
    
    @Test
    public void createAndDestroyMail() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        
        m.getOptions().get(0).execute(world);
        assertEquals(0, mailMgr.getPendingMails().size());
    }
    
    @Test
    public void createAndDestroyMultipleMails() throws Exception
    {
        Mail m1 = mailMgr.createMail(true, "Hello.");
        Mail m2 = mailMgr.createMail(true, "Hello.");
        Mail m3 = mailMgr.createMail(true, "Hello.");
        Mail m4 = mailMgr.createMail(true, "Hello.");
        
        assertEquals(4, mailMgr.getPendingMails().size());
        
        m1.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        m2.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        m3.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        m4.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        
        assertEquals(1, m1.getOptions().size());
        assertEquals(1, m2.getOptions().size());
        assertEquals(1, m3.getOptions().size());
        assertEquals(1, m4.getOptions().size());
        
        m2.getOptions().get(0).execute(world);
        m4.getOptions().get(0).execute(world);
        m3.getOptions().get(0).execute(world);
        m1.getOptions().get(0).execute(world);

        assertEquals(0, mailMgr.getPendingMails().size());
    }
    
    @Test (expected = Mail.InvalidOption.class)
    public void doubleExecute() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        m.getOptions().get(0).execute(world);
        m.getOptions().get(1).execute(world);
    }
    
    @Test (expected = Mail.InvalidOption.class)
    public void disabledExecute() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");

        class Obj { int i = 0; }
        final Obj o = new Obj();
        
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> o.i == 0);
        
        o.i = 1;
        m.getOptions().get(0).execute(world);
    }
    
    @Test
    public void ignoreNonBlockingMail() throws Exception
    {
        Mail m = mailMgr.createMail(false, "Hello.");
        assertEquals(true, world.getTimeMgr().isReadyForNextDay());
        world.getTimeMgr().nextDay();
    }
    
    @Test (expected = TimeMgr.NotReady.class)
    public void ignoreBlockingMail() throws Exception
    {        
        Mail m = mailMgr.createMail(true, "Hello.");
        assertEquals(false, world.getTimeMgr().isReadyForNextDay());
        world.getTimeMgr().nextDay();
    }
    
    @Test
    public void createAndDestroyBlockingMail() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        
        m.getOptions().get(0).execute(world);

        world.getTimeMgr().nextDay();
    }
    
    @Test (expected = SuccessException.class)
    public void executeOptionEffect() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", successEffect, PropertyContext.create(), () -> true);
        
        m.getOptions().get(0).execute(world);        
    }
}
