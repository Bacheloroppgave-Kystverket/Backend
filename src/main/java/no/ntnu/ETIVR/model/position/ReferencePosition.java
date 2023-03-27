package no.ntnu.ETIVR.model.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class ReferencePosition {

  @Id
  @GeneratedValue
  private long locationId;

  private String locationName;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "positionConfigId")
  private PositionConfiguration positionConfiguration;

  /**
   * Makes an instance of the ReferencePosition class.
   */
  public ReferencePosition() {

  }

  /**
   * Makes an instance of the reference position.
   * @param locationId the id of the location.
   * @param locationName the name of the location.
   * @param positionConfiguration the position configuration.
   */
  public ReferencePosition(@JsonProperty("locationID") long locationId,
                           @JsonProperty("locationName") String locationName,
                           @JsonProperty("positionConfiguration") PositionConfiguration positionConfiguration){
    checkFloat(locationId, "location ID");
    checkString(locationName, "location name");
    checkIfObjectIsNull(positionConfiguration, "position configuration");
    this.locationId = locationId;
    this.locationName = locationName;
    this.positionConfiguration = positionConfiguration;
  }

  /**
   * Gets the name of the location.
   * @return the location name.
   */
  public String getLocationName(){
    return locationName;
  }

  /**
   * Gets the position configuration.
   * @return the position configuration.
   */
  public PositionConfiguration getPositionConfiguration() {
    return positionConfiguration;
  }

  /**
   * Gets the location id.
   * @return the location id.
   */
  public long getLocationId(){
    return locationId;
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
