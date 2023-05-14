package no.ntnu.ETIVR.model.trackable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an observation record of a trackable object. Holds all the different positions gazes.
 */
@Entity
public class TrackableRecord {

    @Id
    @GeneratedValue
    private long trackableDataId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackableObjectId")
    @JsonIgnore
    private TrackableObject trackableObject;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gazeLocations", joinColumns = @JoinColumn(name = "trackableDataId"))
    private final List<GazeData> gazeList;

    @Enumerated
    private ViewDistance viewDistance;

    /**
     * Makes an instance of the TrackableLog class.
     * @param gazeList the gaze objects.
     * @param viewDistance the view distance.
     * @param trackableObject the trackable objects.
     */
    public TrackableRecord(@JsonProperty("gazeList") List<GazeData> gazeList,
                           @JsonProperty("viewDistance") ViewDistance viewDistance,
                           @JsonProperty("trackableObject") TrackableObject trackableObject) {
        checkIfObjectIsNull(viewDistance, "view distance");
        checkIfObjectIsNull(gazeList, "gaze list");
        checkIfObjectIsNull(trackableObject, "trackable object");
        this.gazeList = gazeList;
        this.viewDistance = viewDistance;
        this.trackableObject = trackableObject;

    }

    /**
     * Makes an instance of the trackable record class
     */
    public TrackableRecord() {
        this.gazeList = new ArrayList<>();
    }

    /**
     * Get view distance
     * @return view distance
     */
    public ViewDistance getViewDistance() {
        return viewDistance;
    }

    /**
     * Get trackable object id
     * @return trackable object id
     */
    public long getTrackableObjectId() {
        return trackableObject.getTrackableObjectID();
    }

    public long getTrackableDataId() {
        return trackableDataId;
    }

    /**
     * Set view distance
     * @param viewDistance ViewDistance
     */
    public void setViewDistance(ViewDistance viewDistance) {
        this.viewDistance = viewDistance;
    }

    /**
     * Get the gaze list.
     * @return gaze list.
     */
    public List<GazeData> getGazeList() {
        return gazeList;
    }

    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
