package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ReferencePosition.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "sessionId")
    private List<ReferencePosition> referencePositions;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = AdaptiveFeedback.class)
    @JoinColumn(name = "sessionId")
    private List<AdaptiveFeedback> adaptiveFeedbackLog;


    public Session() {
    }

    /**
     * Constructor with parameters
     * @param trackableObjects list of objects to be tracked
     * @param sessionId unique id for user
     * @param adaptiveFeedbackLog the adaptive feedback log
     * @param currentDate the current date.
     * @param referencePositions the reference positions,
     * @param userId the user id.
     */
    public Session(@JsonProperty("currentDate") LocalDateTime currentDate,
                   @JsonProperty("userID") int userId,
                   @JsonProperty("closeTrackableObjects") List<TrackableObject> trackableObjects,
                   @JsonProperty("sessionID") long sessionId,
                   @JsonProperty("referencePositions") List<ReferencePosition> referencePositions,
                   @JsonProperty("feedbackLog") List<AdaptiveFeedback> adaptiveFeedbackLog) {

        this.currentDate = currentDate;

        checkIfObjectIsNull(trackableObjects, "trackable objects");
        this.trackableObjects = trackableObjects;

        checkIfIntNumberNotNegative(userId, "user ID");
        this.userId = userId;

        checkIfNumberNotNegative(sessionId, "session ID");
        this.sessionId = sessionId;

        checkIfObjectIsNull(referencePositions, "reference position");
        this.referencePositions = referencePositions;

        checkIfObjectIsNull(adaptiveFeedbackLog, "feedback log");
        this.adaptiveFeedbackLog = adaptiveFeedbackLog;
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
    public List<AdaptiveFeedback> getFeedbackLog() {
        return adaptiveFeedbackLog;
    }

    /**
     * Set feedback log
     * @param adaptiveFeedbackLog list of feedback logs
     */
    public void setFeedbackLog(List<AdaptiveFeedback> adaptiveFeedbackLog) {
        this.adaptiveFeedbackLog = adaptiveFeedbackLog;
    }


    /**
     * Checks if a string is of a valid format or not.
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
        if (object <= 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
        }
    }

    /**
     * Checks if an input date is before or equal to today's date.
     * @param localDateTime the date to check.
     */
    private void checkIfDateIsBeforeOrEqualToCurrentDate(LocalDateTime localDateTime){
        checkIfObjectIsNull(localDateTime, "date");
        if (LocalDate.now().isBefore(ChronoLocalDate.from(localDateTime))){
            throw new IllegalArgumentException("The set date is after current date.");
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

    /**
     * Checks if an int number is not negative
     * @param object int object to be checked
     * @param error String
     */
    private void checkIfIntNumberNotNegative(int object, String error) {
        if (object <= 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
        }
    }

}



