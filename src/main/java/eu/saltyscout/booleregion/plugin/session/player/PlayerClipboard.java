package eu.saltyscout.booleregion.plugin.session.player;

import eu.saltyscout.booleregion.plugin.session.Clipboard;
import eu.saltyscout.booleregion.region.BooleRegion;

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

    
    public PlayerSession getSession() {
        return session;
    }
}
