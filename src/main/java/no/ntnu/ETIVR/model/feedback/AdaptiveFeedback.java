package no.ntnu.ETIVR.model.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import javax.persistence.*;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class AdaptiveFeedback {

  @Id
  @GeneratedValue
  private int feedbackId;

  @Column(name = "referencePositionId")
  private long referencePositionId;

  private String referencePositionName;

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
   * @param positionTime the time of the position.
   * @param feedbackList the feedback list.
   * @param referencePositionName the reference position of the position.
   */
  public AdaptiveFeedback(@JsonProperty("positionTime") float positionTime ,
                          @JsonProperty("feedbackList") List<CategoryFeedback> feedbackList,
                          @JsonProperty("referencePositionName") String referencePositionName){
    checkIfObjectIsNull(feedbackList, "feedback list");
    checkFloat(positionTime, "Position time");
    checkString(referencePositionName, "reference position");
    this.feedbackList = feedbackList;
    this.positionTime = positionTime;
    this.referencePositionName = referencePositionName;
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
