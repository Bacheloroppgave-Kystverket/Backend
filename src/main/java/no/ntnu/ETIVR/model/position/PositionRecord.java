package no.ntnu.ETIVR.model.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.*;

import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class PositionRecord {

    @Id
    @GeneratedValue
    @Column(name = "posDataId")
    private long posDataId;

    @ManyToOne(targetEntity = ReferencePosition.class)
    @JoinTable(
            name = "recordPositions",
            joinColumns = @JoinColumn(name = "posDataId", referencedColumnName = "posDataId"),
            inverseJoinColumns = @JoinColumn(name = "locationId", referencedColumnName = "locationId")
    )
    private ReferencePosition referencePosition;

    private float positionDuration;

    @OneToMany(cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "positionRecordFeedback",
            joinColumns = @JoinColumn(name = "posDataId", referencedColumnName = "posDataId"),
            inverseJoinColumns = @JoinColumn(name = "feedbackId", referencedColumnName = "feedbackId")
    )
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