/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica.model;

import arsmagica.xml.DataStore;
import arsmagica.xml.PropertyContext;
import arsmagica.xml.Ref;

/**
 *
 * @author Elscouta
 */
public class World 
{
    private final EntityMgr entityMgr;
    private final MailMgr mailMgr;
    private final TimeMgr timeMgr;
    private final EventMgr eventMgr;
    
    public World(DataStore store)
    {
        entityMgr = new EntityMgr(store, this);
        eventMgr = new EventMgr(this);
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
    
    public Entity createEntity(String type)
            throws Ref.Error
    {
        return entityMgr.createNew(type, null, PropertyContext.create());
    }
    
}
