package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Session {

    @Id
    @GeneratedValue
    @Column(name = "sessionId", nullable = false)
    private long sessionId;

    private LocalDateTime currentDate;

    private int userId;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TrackableObject.class)
    @JoinColumn(name = "sessionId")
    private List<TrackableObject> trackableObjects = new ArrayList<>();

    private List<ReferencePosition> referencePositions;

    private List<Feedback> feedbackLog;


    public Session() {
    }

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param totalTime float - time it takes to track objects
     * @param sessionId unique id for user
     */
    public Session(@JsonProperty("closeTrackableObjects") List<TrackableObject> trackableObjects, long sessionId) {
        this.trackableObjects = trackableObjects;
        this.sessionId = sessionId;
    }


    /**
     * Get list of trackable objects
     * @return trackable objects
     */
    public List<TrackableObject> getTrackableObjects() {
        return trackableObjects;
    }

    /**
     * Set the trackable objects
     * @param trackableObjects list of trackable objects
     */
    public void setTrackableObjects(List<TrackableObject> trackableObjects) {
        this.trackableObjects = trackableObjects;
    }

    /**
     * Get the total time
     * @return total time
     */
    public float getTotalTime() {
        return totalTime;
    }

    /**
     * set total time
     * @param totalTime float total time
     */
    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * Get session id
     * @return int session id
     */
    public long getSessionId() {
        return sessionId;
    }

    /**
     * Set user id
     * @param sessionId int
     */
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
}
