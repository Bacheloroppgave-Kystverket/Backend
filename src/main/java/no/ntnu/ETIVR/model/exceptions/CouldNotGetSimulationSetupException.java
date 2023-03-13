package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetSimulationSetupException represents an exception that gets thrown when a simulation setup could not be removed.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetSimulationSetupException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetSimulationSetupException class.
     *
     * @param message the error message.
     */
    public CouldNotGetSimulationSetupException(String message) {
        super(message);

    }
}