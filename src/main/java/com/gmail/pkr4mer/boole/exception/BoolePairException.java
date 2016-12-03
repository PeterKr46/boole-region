package com.gmail.pkr4mer.boole.exception;

/**
 * Created by Peter on 03-Dec-16.
 */
public class BoolePairException extends BooleException {
    private String error;
    public BoolePairException(String error) {
        this.error = error;
    }
    
    @Override
    public String getMessage() {
        return String.format("Issue while pairing up two regions: '%s'", error);
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
}
