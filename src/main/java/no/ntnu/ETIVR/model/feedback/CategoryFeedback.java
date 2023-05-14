package no.ntnu.ETIVR.model.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import no.ntnu.ETIVR.model.trackable.TrackableType;

/**
 * Represents a category and its current time at the recorded point.
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
     *
     * @param trackableType the trackable type.
     * @param time          the time.
     */
    public CategoryFeedback(@JsonProperty("trackableType") TrackableType trackableType, @JsonProperty("time") float time) {
        checkIfObjectIsNull(trackableType, "trackable type");
        checkFloat(time, "time");
        this.time = time;
        this.trackableType = trackableType;
    }

    /**
     * Gets the time of the category feedback.
     *
     * @return the time of the category feedback.
     */
    public float getTime() {
        return time;
    }

    /**
     * Gets the trackable type.
     *
     * @return the trackable type.
     */
    public TrackableType getTrackableType() {
        return trackableType;
    }

    /**
     * Checks if a float is above zero.
     *
     * @param numberToCheck the number to check.
     * @param error         the error.
     */
    private void checkFloat(float numberToCheck, String error) {
        if (numberToCheck < 0) {
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
