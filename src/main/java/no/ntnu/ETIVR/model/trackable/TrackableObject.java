package no.ntnu.ETIVR.model.trackable;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class TrackableObject {

    @Id
    @GeneratedValue
    @Column(name = "trackableObjectID")
    private long trackableObjectID;

    private String nameOfObject;

    @Enumerated
    private TrackableType trackableType;

    /**
     * Constructor with parameters
     * @param nameOfObject String
     * @param trackableType the trackable object type
     * @param trackableObjectID the trackable object id
     */
    public TrackableObject(@JsonProperty("nameOfObject") String nameOfObject,
                           @JsonProperty("trackableType") TrackableType trackableType,
                           @JsonProperty("trackableObjectID") long trackableObjectID) {
        checkString(nameOfObject, "name of object");
        this.nameOfObject = nameOfObject;
        checkIfObjectIsNull(trackableType, "trackable type");
        this.trackableType = trackableType;
        checkIfNumberNotNegative(trackableObjectID, "trackable object ID");
        this.trackableObjectID = trackableObjectID;
    }

    /**
     * Empty constructor for the JPA library.
     */
    public TrackableObject() {

    }

    /**
     * Get name of object
     * @return name of object
     */
    public String getNameOfObject() {
        return nameOfObject;
    }

    /**
     * Set name of object
     * @param nameOfObject String
     */
    public void setNameOfObject(String nameOfObject) {
        this.nameOfObject = nameOfObject;
    }

    /**
     * Get trackable type
     * @return trackable type
     */
    public TrackableType getTrackableType() {
        return trackableType;
    }

    /**
     * Set trackable type
     * @param trackableType TrackableType
     */
    public void setTrackableType(TrackableType trackableType) {
        this.trackableType = trackableType;
    }

    /**
     * Get trackable object by ID
     * @return trackable object
     */
    public long getTrackableObjectID() {
        return trackableObjectID;
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
     * Check to make sure that integer values cannot be negative.
     * @param object the object to be checked.
     * @param error exception message to be displayed.
     */
    private void checkIfNumberNotNegative(long object, String error) {
        if (object < 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
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
