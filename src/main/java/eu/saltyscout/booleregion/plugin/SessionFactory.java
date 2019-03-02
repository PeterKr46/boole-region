package eu.saltyscout.booleregion.plugin;

import eu.saltyscout.booleregion.plugin.session.Session;
import eu.saltyscout.booleregion.plugin.session.player.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Peter on 03-Dec-16.
 */
public class SessionFactory {
    private HashMap<UUID, Session> sessions;
    
    SessionFactory() {
        sessions = new HashMap<>();
    }
    
    private Session constructSession(UUID uuid) {
        Session session = null;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if(offlinePlayer.hasPlayedBefore()) {
            session = new PlayerSession(offlinePlayer);
        }
        return session;
    }
    
    public Session getSession(UUID uuid) {
        Session session;
        if(!sessions.containsKey(uuid)) {
            session = constructSession(uuid);
            sessions.put(uuid, session);
        } else {
            session = sessions.get(uuid);
        }
        return session;
    }
    
    int purgeSessions(long maximumAge) {
        int purged = 0;
        long deadline = System.currentTimeMillis() - maximumAge;
        Collection<Session> sessions = new ArrayList<>(this.sessions.values());
        for(Session session : sessions) {
            if(!session.isOwnerOnline() && session.getSessionStart() < deadline) {
                this.sessions.remove(session.getOwner());
                purged++;
            }
        }
        return purged;
    }
    
}
