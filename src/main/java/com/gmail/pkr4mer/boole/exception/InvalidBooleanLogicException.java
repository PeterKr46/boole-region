package com.gmail.pkr4mer.boole.exception;

/**
 * Created by Peter on 03-Dec-16.
 */
public class InvalidBooleanLogicException extends BooleException {
    private String logic;
    public InvalidBooleanLogicException(String logic) {
        this.logic = logic;
    }
    
    @Override
    public String getMessage() {
        return String.format("Could not make sense of the following statement: '%s'", logic);
    }
    
    public String getLogic() {
        return logic;
    }
    
    public void setLogic(String logic) {
        this.logic = logic;
    }
}
