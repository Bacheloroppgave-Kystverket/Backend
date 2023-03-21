package no.ntnu.ETIVR.model.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import no.ntnu.ETIVR.model.trackable.TrackableType;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Embeddable
public class CategoryConfiguration {

  @Enumerated
  private TrackableType trackableType;

  private float threshold;

  /**
   * Makes an instance of the FeedbackConfiguration class.
   */
  public CategoryConfiguration() {

  }

  /**
   * Makes an instance of the FeedbackConfiguration
   * @param trackableType the trackable type
   * @param threshold the threshold
   */
  public CategoryConfiguration(@JsonProperty("trackableType") TrackableType trackableType,
                               @JsonProperty("threshold") float threshold){
    checkIfObjectIsNull(trackableType, "trackable type");
    checkIfFloatIsBetweenZeroAndOne(threshold, "threshold");
    this.trackableType = trackableType;
    this.threshold = threshold;
  }

  /**
   * Checks if a number is between zero and one.
   * @param number the number to check.
   * @param error the error.
   */
  private void checkIfFloatIsBetweenZeroAndOne(float number, String error){
    checkFloat(number, error);
    if(number > 1){
      throw new IllegalArgumentException("The number must be below 1.");
    }
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
