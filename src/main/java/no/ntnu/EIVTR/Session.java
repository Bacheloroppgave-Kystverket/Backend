package no.ntnu.EIVTR;

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
    @Column(name = "userId", nullable = false)
    private int userId;

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param totalTime float - time it takes to track objects
     * @param userId unique id for user
     */
    public Session(List<TrackableObjects> trackableObjects, float totalTime, int userId) {
        this.trackableObjects = trackableObjects;
        this.totalTime = totalTime;
        this.userId = userId;
    }

    /**
     * Empty constructor
     */
    public Session() {
    }

    /**
     * Get list of trackable objects
     * @return trackable objects
     */
    public List<TrackableObjects> getTrackableObjects() {
        return trackableObjects;
    }

    /**
     * set the trackable objects
     * @param trackableObjects !TODO
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
     * Get user id
     * @return int user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user id
     * @param userId int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
