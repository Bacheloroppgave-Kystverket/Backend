package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class PositionLog {

    @Id
    @GeneratedValue
    private long positionLogId;

    @ManyToOne
    @JoinColumn(name = "referencePositionId")
    private ReferencePosition referencePosition;

    private float positionDuration;

    private PositionConfiguration positionConfiguration;

    @ElementCollection
    @CollectionTable(name = "adaptiveFeedbackForPositions", joinColumns = @JoinColumn(name = "positionLogId"))
    private List<AdaptiveFeedback> adaptiveFeedbacks;

    /**
     * Makes an instance of the PositionLog class.
     */
    public PositionLog() {

    }

    /**
     * Makes an instance of the PositionLog class.
     * @param referencePosition the reference position of the log.
     * @param positionDuration the duration of the position log.
     * @param positionConfiguration the position configuration.
     */
    public PositionLog(@JsonProperty("referencePosition") ReferencePosition referencePosition,
                       @JsonProperty("positionDuration") float positionDuration,
                       @JsonProperty("positionConfiguration") PositionConfiguration positionConfiguration) {
        checkFloat(positionDuration, "position duration");
        checkIfObjectIsNull(positionConfiguration, "feedback configurations");
        checkIfObjectIsNull(referencePosition, "reference position");
        checkIfObjectIsNull(positionConfiguration, "position configuration");
        this.referencePosition = referencePosition;
        this.positionLogId = 0;
        this.positionDuration = positionDuration;
        this.positionConfiguration = positionConfiguration;
    }

    /**
     * Gets the position duration.
     * @return the position duration.
     */
    public float getPositionDuration(){
        return positionDuration;
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
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
