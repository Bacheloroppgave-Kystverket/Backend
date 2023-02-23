package no.ntnu.ETIVR.model;

import java.lang.ref.Reference;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class ReferencePosition {

  private String locationId;

  private String locationName;

  private float positionDuration;

  /**
   * Makes an instance of the ReferencePosition class.
   */
  public ReferencePosition() {

  }

  /**
   * Makes an instance of the reference position.
   * @param locationId the id of the location
   * @param locationName the name of the location
   * @param positionDuration the time spent at position
   */
  public ReferencePosition(String locationId, String locationName, float positionDuration){
    checkString(locationId, "location ID");
    checkString(locationName, locationName);
    checkFloat(positionDuration, "position duration");
  }

  /**
   * Checks if a float is above zero.
   * @param numberToCheck the number to check.
   * @param error the error.
   */
  public void checkFloat(float numberToCheck, String error){
    if(numberToCheck < 0){
      throw new IllegalArgumentException("The " + error + " cannot be below zero");
    }
  }

  /**
   * Checks if a string is of a valid format or not.
   *
   * @param stringToCheck the string you want to check.
   * @param errorPrefix   the error the exception should have if the string is invalid.
   * @throws IllegalArgumentException gets thrown if the string to check is empty or null.
   */
  private void checkString(String stringToCheck, String errorPrefix) {
    checkIfObjectIsNull(stringToCheck, errorPrefix);
    if (stringToCheck.isEmpty()) {
      throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
    }
  }

  /**
   * Checks if an object is null.
   *
   * @param object the object you want to check.
   * @param error  the error message the exception should have.
   * @throws IllegalArgumentException gets thrown if the object is null.
   */
  private void checkIfObjectIsNull(Object object, String error) {
    if (object == null) {
      throw new IllegalArgumentException("The " + error + " cannot be null.");
    }
  }
}
