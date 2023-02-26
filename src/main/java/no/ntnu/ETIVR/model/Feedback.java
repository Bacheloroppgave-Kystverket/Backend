package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.sound.midi.Track;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class Feedback {

  @Id
  @GeneratedValue
  private int feedbackId;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "calculatedFeedbacks", joinColumns = @JoinColumn(name = "feedbackId"))
  private List<CalculatedFeedback> feedbackList;

  /**
   * Makes an instance of the Feedback class.
   */
  public Feedback(){
    
  }

  /**
   * Makes an instance of the Feedback class.
   * @param feedbackList the feedback list.
   */
  public Feedback(@JsonProperty("feedbackList") List<CalculatedFeedback> feedbackList){
    checkIfObjectIsNull(feedbackList, "feedback list");
    this.feedbackList = feedbackList;
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
