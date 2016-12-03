package com.gmail.pkr4mer.boole.plugin;

import com.gmail.pkr4mer.boole.plugin.session.Session;
import com.gmail.pkr4mer.boole.plugin.session.player.PlayerSession;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Collection<Session> sessions = this.sessions.values().stream().collect(Collectors.toList());
        for(Session session : sessions) {
            if(!session.isOwnerOnline() && session.getSessionStart() < deadline) {
                this.sessions.remove(session.getOwner());
                purged++;
            }
        }
        return purged;
    }
    
}
