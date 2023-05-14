package no.ntnu.ETIVR.model.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import javax.persistence.*;

/**
 * Represents a class that holds all the feedback that was given at a time. Shows the different categories.
 */
@Entity
public class AdaptiveFeedback {

    @Id
    @GeneratedValue
    private int feedbackId;

    private float positionTime;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "categoryFeedbacks", joinColumns = @JoinColumn(name = "feedbackId"))
    private List<CategoryFeedback> feedbackList;

    /**
     * Makes an instance of the Feedback class.
     */
    public AdaptiveFeedback() {
        this.feedbackId = 0;
    }

    /**
     * Makes an instance of the Feedback class.
     * @param positionTime the time of the position.
     * @param feedbackList the feedback list.
     */
    public AdaptiveFeedback(@JsonProperty("positionTime") float positionTime,
                            @JsonProperty("feedbackList") List<CategoryFeedback> feedbackList) {
        checkIfObjectIsNull(feedbackList, "feedback list");
        checkFloat(positionTime, "Position time");
        this.feedbackList = feedbackList;
        this.positionTime = positionTime;
        this.feedbackId = 0;
    }

    /**
     * Gets the feedback id.
     * @return the id.
     */
    public int getFeedbackId() {
        return feedbackId;
    }

    /**
     * Gets the time of the position.
     * @return the time for the position.
     */
    public float getPositionTime() {
        return positionTime;
    }

    /**
     * Gets the feedback list.
     * @return the feedback list.
     */
    public List<CategoryFeedback> getFeedbackList() {
        return feedbackList;
    }

    /**
     * Checks if a float is above zero.
     * @param numberToCheck the number to check.
     * @param error the error.
     */
    private void checkFloat(float numberToCheck, String error) {
        if (numberToCheck < 0) {
            throw new IllegalArgumentException("The " + error + " cannot be below zero");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
