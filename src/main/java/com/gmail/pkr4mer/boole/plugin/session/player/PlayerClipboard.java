package com.gmail.pkr4mer.boole.plugin.session.player;

import com.gmail.pkr4mer.boole.plugin.session.Clipboard;
import com.gmail.pkr4mer.boole.region.BooleRegion;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Peter on 03-Dec-16.
 */
public class PlayerClipboard extends HashMap<String, BooleRegion> implements Clipboard {
    private final PlayerSession session;
    
    PlayerClipboard(PlayerSession session) {
        super(4);
        this.session = session;
    }
    
    @Override
    public boolean isUsed(String storageId) {
        return super.containsKey(storageId);
    }
    
    @Override
    public boolean storeRegion(String storageId, BooleRegion region) {
        return region != null && storageId != null && !storageId.contains(" ") && super.put(storageId, region) == null;
    }
    
    @Override
    public BooleRegion getRegion(String storageId) {
        return super.get(storageId);
    }
    
    @Override
    public Collection<String> getKeys() {
        return super.keySet();
    }
    
    @Override
    public Collection<BooleRegion> getValues() {
        return super.values();
    }
    
    public PlayerSession getSession() {
        return session;
    }
}
