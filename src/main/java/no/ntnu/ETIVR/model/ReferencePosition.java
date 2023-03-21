package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.ref.Reference;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Embeddable
public class ReferencePosition {

  private long locationId;

  private String locationName;

  /**
   * Makes an instance of the ReferencePosition class.
   */
  public ReferencePosition() {

  }

  /**
   * Makes an instance of the reference position
   * @param locationId the id of the location
   * @param locationName the name of the location
   */
  public ReferencePosition(@JsonProperty("locationID") long locationId,
                           @JsonProperty("locationName") String locationName){
    checkFloat(locationId, "location ID");
    checkString(locationName, "location name");
    this.locationId = locationId;
    this.locationName = locationName;
  }

  /**
   * Gets the name of the location.
   * @return the location name.
   */
  public String getLocationName(){
    return locationName;
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
