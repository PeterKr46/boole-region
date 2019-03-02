package eu.saltyscout.booleregion.exception;

/**
 * Created by Peter on 03-Dec-16.
 */
public class IncompleteSelectionException extends BooleException {
    @Override
    public String getMessage() {
        return "Incomplete Selection!";
    }
}
