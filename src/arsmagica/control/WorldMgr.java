/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.control;

import arsmagica.model.objects.Entity;
import arsmagica.xml.DataStore;
import arsmagica.model.objects.PropertyContext;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public class WorldMgr 
{
    private final EntityMgr entityMgr;
    private final MailMgr mailMgr;
    private final TimeMgr timeMgr;
    private final EventMgr eventMgr;
    
    public WorldMgr(DataStore store)
    {
        eventMgr = new EventMgr(this);
        entityMgr = new EntityMgr(store, eventMgr, this);
        mailMgr = new MailMgr(this);
        timeMgr = new TimeMgr();
        
        timeMgr.addListener(mailMgr);
        timeMgr.addListener(eventMgr);
    }
    
    public EntityMgr getEntityMgr()
    {
        return entityMgr;
    }
    
    public MailMgr getMailMgr()
    {
        return mailMgr;
    }
    
    public TimeMgr getTimeMgr()
    {
        return timeMgr;
    }
    
    public Entity createEntity(String type)
            throws Ref.Error
    {
        return entityMgr.createNew(type, null, PropertyContext.create());
    }
}
