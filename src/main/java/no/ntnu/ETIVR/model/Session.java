package no.ntnu.ETIVR.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;

@Entity
public class Session {
    @Transient
    List<TrackableObject> trackableObjects = new ArrayList<>();
    private float totalTime;

    @Id
    @GeneratedValue
    @Column(name = "sessionId", nullable = false)
    private long sessionId;

    public Session() {
    }

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param totalTime float - time it takes to track objects
     * @param sessionId unique id for user
     */
    public Session(List<TrackableObject> trackableObjects, float totalTime, long sessionId) {
        this.trackableObjects = trackableObjects;
        this.totalTime = totalTime;
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
