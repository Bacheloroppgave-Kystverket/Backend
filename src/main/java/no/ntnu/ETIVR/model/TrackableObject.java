package no.ntnu.ETIVR.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TrackableObject {

    ///Id her er bare så jeg får testa
    @Id
    private String nameOfObject;

    private long trackableObjectID;

    private TrackableType trackableType;

    private ViewDistance viewDistance;

    private final List<GazeData> gazeList = new ArrayList<>();


    public TrackableObject() {
    }

    /**
     * Constructor with parameters
     * @param nameOfObject String
     */
    public TrackableObject(String nameOfObject, TrackableType trackableType, ViewDistance viewDistance, long trackableObjectID) {
        this.nameOfObject = nameOfObject;
        this.trackableType = TrackableType.UNDEFINED;
        this.viewDistance = viewDistance;
        this.trackableObjectID = trackableObjectID;
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

    public TrackableType getTrackableType() {
        return trackableType;
    }

    public void setTrackableType(TrackableType trackableType) {
        this.trackableType = trackableType;
    }

    public ViewDistance getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(ViewDistance viewDistance) {
        this.viewDistance = viewDistance;
    }

    public List<GazeData> getGazeList() {
        return gazeList;
    }

    public long getTrackableObjectID() {
        return trackableObjectID;
    }
}
