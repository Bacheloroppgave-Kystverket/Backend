package no.ntnu.ETIVR.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;

@Entity
public class TrackableObject {

    ///Id her er bare så jeg får test.
    private String nameOfObject;

    @Id
    @GeneratedValue
    @Column(name = "trackableObjectID")
    private long trackableObjectID;

    @Enumerated
    private TrackableType trackableType;

    @Enumerated
    private ViewDistance viewDistance;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gazeLocations", joinColumns = @JoinColumn(name = "trackableObjectID"))
    private final List<GazeData> gazeList;


    public TrackableObject() {
        this.gazeList = new ArrayList<>();
    }

    /**
     * Constructor with parameters
     * @param nameOfObject
     */
    public TrackableObject(String nameOfObject, TrackableType trackableType, ViewDistance viewDistance, long trackableObjectID) {
        this.gazeList = new ArrayList<>();
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
