package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class AdaptiveFeedback {

  @Id
  @GeneratedValue
  private int feedbackId;

  private String positionName;

  private float positionTime;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "calculatedFeedbacks", joinColumns = @JoinColumn(name = "feedbackId"))
  private List<CategoryFeedback> feedbackList;

  /**
   * Makes an instance of the Feedback class.
   */
  public AdaptiveFeedback(){
    
  }

  /**
   * Makes an instance of the Feedback class.
   * @param positionName the name of the position.
   * @param positionTime the time of the position.
   * @param feedbackList the feedback list.
   */
  public AdaptiveFeedback(@JsonProperty("positionName") String positionName,
                          @JsonProperty("positionTime") float positionTime ,
                          @JsonProperty("feedbackList") List<CategoryFeedback> feedbackList){
    checkIfObjectIsNull(feedbackList, "feedback list");
    checkString(positionName, "position name");
    checkFloat(positionTime, "Position time");
    this.feedbackList = feedbackList;
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
