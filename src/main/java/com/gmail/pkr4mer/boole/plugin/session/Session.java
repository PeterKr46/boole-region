package com.gmail.pkr4mer.boole.plugin.session;


import java.util.UUID;

/**
 * Created by Peter on 03-Dec-16.
 */
public interface Session {
    
    /**
     * @return the time the session was started as a UNIX timestamp.
     */
    long getSessionStart();
    
    /**
     * @return the Clipboard for this Session.
     */
    Clipboard getClipboard();
    
    /**
     *
     * @return the Player this Session belongs to.
     */
    UUID getOwner();
    
    /**
     * @return true if the Owner of this Session is online.
     */
    boolean isOwnerOnline();
}
