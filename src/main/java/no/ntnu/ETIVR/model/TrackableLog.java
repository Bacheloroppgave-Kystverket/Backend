package no.ntnu.ETIVR.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class TrackableLog implements Log<TrackableData> {

  @Id
  @GeneratedValue
  private long trackableLogId;
  @OneToMany(cascade = CascadeType.ALL, targetEntity = TrackableData.class)
  @JoinColumn(name = "sessionId")
  private List<TrackableData> trackableDataList;

  /**
   * Makes an instance of the TrackableLog class.
   */
  public TrackableLog() {

  }

  /**
   * Makes an instance of the trackable log.
   * @param trackableDataList the trackable data list.
   */
  public TrackableLog(List<TrackableData> trackableDataList){
    checkIfObjectIsNull(trackableDataList, "trackable data list");
    this.trackableDataList = trackableDataList;
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

  @Override
  public List<TrackableData> getLog() {
    return trackableDataList;
  }
}
