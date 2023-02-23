package no.ntnu.ETIVR.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddUserException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddUserException extends Exception implements Serializable {

  /**
   * Makes an instance of the CouldNotAddUserException class.
   *
   * @param message the error message.
   */
  public CouldNotAddUserException(String message) {
    super(message);

  }
}