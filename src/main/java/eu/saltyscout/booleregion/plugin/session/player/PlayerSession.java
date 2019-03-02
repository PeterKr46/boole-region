package eu.saltyscout.booleregion.plugin.session.player;

import eu.saltyscout.booleregion.plugin.session.Clipboard;
import eu.saltyscout.booleregion.plugin.session.Session;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

/**
 * Created by Peter on 03-Dec-16.
 */
public class PlayerSession implements Session {
    private final long started;
    private final Clipboard clipboard;
    private final UUID ownerUUID;
    private final String ownerName;
    
    public PlayerSession(OfflinePlayer player) {
        started = System.currentTimeMillis();
        clipboard = new PlayerClipboard(this);
        ownerUUID = player.getUniqueId();
        ownerName = player.getName();
    }
    
    @Override
    public long getSessionStart() {
        return started;
    }
    
    @Override
    public Clipboard getClipboard() {
        return clipboard;
    }
    
    @Override
    public UUID getOwner() {
        return ownerUUID;
    }
    
    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(ownerUUID);
    }
    
    @Override
    public boolean isOwnerOnline() {
        return getPlayer().isOnline();
    }
    
    public String getOwnerName() {
        return ownerName;
    }
}
