/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.control;

import arsmagica.desc.effects.Effect;
import arsmagica.desc.effects.EffectList;
import arsmagica.xml.DataStore;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Elscouta
 */
public class TimeMgrTest 
{
    private WorldMgr world;
    private TimeMgr timeMgr;
    private Effect emptyEffect;
    private TimeMgr.Listener notReadyListener;
    
    private static class SuccessException extends RuntimeException {}
    
    @Before
    public void setUp()
    {
        DataStore store = new DataStore("");
        world = new WorldMgr(store);
        timeMgr = world.getTimeMgr();
        emptyEffect = new EffectList(new ArrayList<>());
        notReadyListener = new TimeMgr.Listener() {
            @Override public boolean isReady() { return false; }
            @Override public void closeDay() throws TimeMgr.NotReady { throw new TimeMgr.NotReady(""); }
        };
    }

    @Test (expected = SuccessException.class)
    public void listenerCallCloseDay() throws Exception
    {
        timeMgr.addListener(new TimeMgr.Listener() {
           @Override public void closeDay() { throw new SuccessException(); } 
           @Override public void openDay() { throw new RuntimeException(); }
        });
        assertEquals(true, timeMgr.isReadyForNextDay());
        timeMgr.nextDay();
    }
    
    @Test (expected = SuccessException.class)
    public void listenerCallOpenDay() throws Exception
    {
        timeMgr.addListener(new TimeMgr.Listener() {
           @Override public void openDay() { throw new SuccessException(); }
        });
        assertEquals(true, timeMgr.isReadyForNextDay());
        timeMgr.nextDay();
    }
    
    @Test (expected = TimeMgr.NotReady.class)
    public void listenerNotReadyCrash() throws Exception
    {
        timeMgr.addListener(notReadyListener);
        timeMgr.nextDay();
    }

    public void listenerNotReadyCall() throws Exception
    {
        timeMgr.addListener(notReadyListener);
        assertEquals(false, timeMgr.isReadyForNextDay());
    }
}
