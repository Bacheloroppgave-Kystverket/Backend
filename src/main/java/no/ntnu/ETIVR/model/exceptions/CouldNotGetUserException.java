package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetUserException represents an exception that gets thrown when
 */
public class CouldNotGetUserException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetUserException class.
     * @param message the error message.
     */
    public CouldNotGetUserException(String message) {
        super(message);

    }
}