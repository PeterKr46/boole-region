package com.gmail.pkr4mer.boole.exception;

/**
 * Created by Peter on 03-Dec-16.
 */
public class InvalidSessionException extends BooleException {
    @Override
    public String getMessage() {
        return "Invalid Session (I'm having issue communicating with WorldEdit).";
    }
}
