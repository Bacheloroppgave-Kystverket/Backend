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
import javax.persistence.Transient;

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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ReferencePosition.class)
    @JoinColumn(name = "sessionId")
    private List<ReferencePosition> referencePositions;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sessionFeedbackConfiguration", joinColumns = @JoinColumn(name = "sessionId"))
    private List<FeedbackConfiguration> feedbackConfigurations;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Feedback.class)
    @JoinColumn(name = "sessionId")
    private List<Feedback> feedbackLog;


    public Session() {
    }

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param sessionId unique id for user
     */
    public Session(@JsonProperty("currentDate") LocalDateTime currentDate,
                   @JsonProperty("userID") int userId,
                   @JsonProperty("closeTrackableObjects") List<TrackableObject> trackableObjects,
                   @JsonProperty("sessionID") long sessionId,
                   @JsonProperty("referencePositions") List<ReferencePosition> referencePositions,
                   @JsonProperty("feedbackLog") List<Feedback> feedbackLog,
                   @JsonProperty("feedbackConfigurations") List<FeedbackConfiguration> feedbackConfigurations) {
        this.currentDate = currentDate;
        this.trackableObjects = trackableObjects;
        this.userId = userId;
        this.sessionId = sessionId;
        this.referencePositions = referencePositions;
        this.feedbackLog = feedbackLog;
        this.feedbackConfigurations = feedbackConfigurations;
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

    /**
     * Get current date
     * @return current date
     */
    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    /**
     * Set current date
     * @param currentDate current date
     */
    public void setCurrentDate(LocalDateTime currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * Get user ID
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Set user ID
     * @param userId int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Get reference position
     * @return reference position
     */
    public List<ReferencePosition> getReferencePositions() {
        return referencePositions;
    }

    /**
     * Set reference position
     * @param referencePositions list of reference position
     */
    public void setReferencePositions(List<ReferencePosition> referencePositions) {
        this.referencePositions = referencePositions;
    }

    /**
     * Get feedback log
     * @return feedback log
     */
    public List<Feedback> getFeedbackLog() {
        return feedbackLog;
    }

    /**
     * Set feedback log
     * @param feedbackLog list of feedback logs
     */
    public void setFeedbackLog(List<Feedback> feedbackLog) {
        this.feedbackLog = feedbackLog;
    }
}



