package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddSimulationSetupException represents an exception that gets thrown when a simulation setup could not be added.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddSimulationSetupException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddSimulationSetupException class.
     *
     * @param message the error message.
     */
    public CouldNotAddSimulationSetupException(String message) {
        super(message);

    }
}