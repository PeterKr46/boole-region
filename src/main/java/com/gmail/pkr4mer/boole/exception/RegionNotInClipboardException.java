package com.gmail.pkr4mer.boole.exception;

/**
 * Created by Peter on 03-Dec-16.
 */
public class RegionNotInClipboardException extends BooleException {
    private String storageId;
    public RegionNotInClipboardException(String storageId) {
        this.storageId = storageId;
    }
    
    @Override
    public String getMessage() {
        return String.format("The region '%s' was not found in the clipboard.", storageId);
    }
    
    public String getStorageId() {
        return storageId;
    }
    
    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }
}
