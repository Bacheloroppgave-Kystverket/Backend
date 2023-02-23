package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.Track;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class Feedback {

  private Map<TrackableType, Float> hashMap;

  /**
   * Makes an instance of the Feedback class.
   */
  public Feedback() {

  }

  /**
   * Makes an instance of the Feedback class.
   * @param hashMap the hashmap
   */
  public Feedback(@JsonProperty("hashMap") Map<TrackableType, Float> hashMap){
    checkIfObjectIsNull(hashMap, "Hashmap");
    this.hashMap = hashMap;
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
