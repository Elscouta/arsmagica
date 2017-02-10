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
    }
    
    @Test
    public void ignoreNonBlockingMail() throws Exception
    {
        Mail m = mailMgr.createMail(false, "Hello.");
        world.nextDay();
    }
    
    @Test (expected = TimeMgr.NotReady.class)
    public void ignoreBlockingMail() throws Exception
    {        
        Mail m = mailMgr.createMail(true, "Hello.");
        world.nextDay();
    }
    
    @Test
    public void createAndDestroyBlockingMail() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", emptyEffect, PropertyContext.create(), () -> true);
        
        m.getOptions().get(0).execute(world);

        world.nextDay();
    }
    
    @Test (expected = SuccessException.class)
    public void executeOptionEffect() throws Exception
    {
        Mail m = mailMgr.createMail(true, "Hello.");
        m.addOption("I <3 you", successEffect, PropertyContext.create(), () -> true);
        
        m.getOptions().get(0).execute(world);        
    }
}
