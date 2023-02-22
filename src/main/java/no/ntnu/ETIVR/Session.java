package no.ntnu.ETIVR;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


public class Session {
    List<TrackableObjects> trackableObjects = new ArrayList<>();
    private float totalTime;

    @Id
    @GeneratedValue
    @Column(name = "sessionId", nullable = false)
    private long sessionId;

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param totalTime float - time it takes to track objects
     * @param sessionId unique id for user
     */
    public Session(List<TrackableObjects> trackableObjects, float totalTime, long sessionId) {
        this.trackableObjects = trackableObjects;
        this.totalTime = totalTime;
        this.sessionId = sessionId;
    }


    /**
     * Get list of trackable objects
     * @return trackable objects
     */
    public List<TrackableObjects> getTrackableObjects() {
        return trackableObjects;
    }

    /**
     * Set the trackable objects
     * @param trackableObjects list of trackable objects
     */
    public void setTrackableObjects(List<TrackableObjects> trackableObjects) {
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
