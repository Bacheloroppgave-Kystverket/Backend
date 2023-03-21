package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import no.ntnu.ETIVR.model.TrackableObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class TrackableLog {

    @Id
    @GeneratedValue
    @Column(name = "trackableLogID")
    private long trackableLogID;

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "trackableLogID")
    @Transient
    private TrackableObject trackableObject;

    //@ElementCollection(fetch = FetchType.EAGER)
    //@CollectionTable(name = "gazeLocations", joinColumns = @JoinColumn(name = "trackableLogID"))
    @Transient
    private final List<GazeData> gazeList;

    @Enumerated
    private ViewDistance viewDistance;

    /**
     * Makes an instance of the TrackableLog class.
     * @param gazeList
     * @param viewDistance
     * @param trackableObject
     */
    public TrackableLog(@JsonProperty("gazeList") List<GazeData> gazeList,
                        @JsonProperty("viewDistance") ViewDistance viewDistance,
                        @JsonProperty("trackableObject") TrackableObject trackableObject) {
        checkIfObjectIsNull(viewDistance, "view distance");
        checkIfObjectIsNull(gazeList, "gaze list");
        checkIfObjectIsNull(trackableObject, "trackable object");
        this.gazeList = gazeList;
        this.viewDistance = viewDistance;

    }

    public TrackableLog(){
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
     * Get the gaze list.
     * @return gaze list.
     */
    public List<GazeData> getGazeList() {
        return gazeList;
    }

    /**
     * Gets the trackable object this log represents.
     * @return the trackable object.
     */
    public TrackableObject getTrackableObject() {
        return trackableObject;
    }

    /**
     * Set view distance
     * @param viewDistance ViewDistance
     */
    public void setViewDistance(ViewDistance viewDistance) {
        this.viewDistance = viewDistance;
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
