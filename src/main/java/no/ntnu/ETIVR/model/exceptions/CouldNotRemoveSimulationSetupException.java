package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveSimulationSetupException represents an exception that gets thrown when a simulation setup could not be removed.
 */
public class CouldNotRemoveSimulationSetupException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveSimulationSetupException class.
     * @param message the error message.
     */
    public CouldNotRemoveSimulationSetupException(String message) {
        super(message);

    }
}