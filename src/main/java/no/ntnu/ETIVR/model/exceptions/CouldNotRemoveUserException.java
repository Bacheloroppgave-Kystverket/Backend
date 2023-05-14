package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveUserException represents an exception that gets thrown when a user could not be removed.
 */
public class CouldNotRemoveUserException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveUserException class.
     * @param message the error message.
     */
    public CouldNotRemoveUserException(String message) {
        super(message);

    }
}