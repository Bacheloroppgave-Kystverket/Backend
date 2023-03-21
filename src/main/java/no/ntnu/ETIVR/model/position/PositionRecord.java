package no.ntnu.ETIVR.model.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class PositionRecord {

    @Id
    @GeneratedValue
    private long posDataId;

    //@ManyToOne(targetEntity = ReferencePosition.class)
    //@JoinColumn(name = "locationId")
    @Transient
    private ReferencePosition referencePosition;
    private float positionDuration;
    //@ElementCollection
    //@CollectionTable(name = "adaptiveFeedbackForPositions", joinColumns = @JoinColumn(name = "positionLogId"))
    @Transient
    private List<AdaptiveFeedback> adaptiveFeedbacks;

    public List<AdaptiveFeedback> getAdaptiveFeedbacks() {
        return adaptiveFeedbacks;
    }

    /**
     * Makes an instance of the PositionData class.
     */
    public PositionRecord() {
    }

    /**
     * Makes an instance of the PositionData class.
     * @param referencePosition the reference position of the log.
     * @param positionDuration the duration of the position log.
     * @param adaptiveFeedbacks the adaptive feedbacks.
     */
    public PositionRecord(@JsonProperty("referencePosition") ReferencePosition referencePosition,
                          @JsonProperty("positionDuration") float positionDuration,
                          @JsonProperty("adaptiveFeedbacks") List<AdaptiveFeedback> adaptiveFeedbacks){
        checkFloat(positionDuration, "position duration");
        checkIfObjectIsNull(referencePosition, "reference position");
        checkIfObjectIsNull(adaptiveFeedbacks, "adaptive feedbacks");
        this.referencePosition = referencePosition;
        this.positionDuration = positionDuration;
        this.adaptiveFeedbacks = adaptiveFeedbacks;
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