package com.gmail.pkr4mer.boole.plugin.session;

import com.gmail.pkr4mer.boole.region.BooleRegion;

import java.util.Collection;

/**
 * Created by Peter on 03-Dec-16.
 */
public interface Clipboard {
    boolean isEmpty();
    
    boolean isUsed(String storageId);
    
    boolean storeRegion(String storageId, BooleRegion region);
    
    BooleRegion getRegion(String storageId);
    
    Collection<String> getKeys();
    
    Collection<BooleRegion> getValues();
    
}
