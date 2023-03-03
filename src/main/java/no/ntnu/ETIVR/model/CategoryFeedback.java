package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Embeddable
public class CategoryFeedback {

  @Enumerated
  private TrackableType trackableType;

  private float time;

  /**
   * Makes an instance of the CalculatedFeedback class.
   */
  public CategoryFeedback() {

  }

  /**
   * Makes an instance of the Calculated Feedback
   * @param trackableType the trackable type.
   * @param time the time.
   */
  public CategoryFeedback(@JsonProperty("trackableType") TrackableType trackableType, @JsonProperty("time") float time){
    checkIfObjectIsNull(trackableType, "trackable type");
    checkFloat(time, "time");
    this.time = time;
    this.trackableType = trackableType;
  }

  /**
   * Checks if a float is above zero.
   * @param numberToCheck the number to check.
   * @param error the error.
   */
  private void checkFloat(float numberToCheck, String error){
    if(numberToCheck < 0){
      throw new IllegalArgumentException("The " + error + " cannot be below zero");
    }
  }

  /**
   * Checks if a string is of a valid format or not.
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
