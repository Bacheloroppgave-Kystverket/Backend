package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Session {

    @Id
    @GeneratedValue
    @Column(name = "sessionId", insertable = false, updatable = false)
    private long sessionId;

    private LocalDateTime currentDate;

    @ManyToOne(targetEntity = User.class)
    @JoinTable(
        name = "sessionsOfUser",
        joinColumns = @JoinColumn(name = "sessionId", referencedColumnName = "sessionId"),
        inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId")
    )
    private User user;

    //@OneToMany(cascade = CascadeType.ALL, targetEntity = TrackableLog.class)
    //@JoinColumn(name = "sessionId")
    @Transient
    private List<TrackableLog> trackableLogs;

    //@OneToMany(cascade = CascadeType.ALL, targetEntity = AdaptiveFeedback.class)
    //@JoinColumn(name = "sessionId")
    @Transient
    private List<AdaptiveFeedback> adaptiveFeedbackLog;

    //@ManyToOne
    //@JoinColumn(name = "session")
    @Transient
    private SimulationSetup simulationSetup;

    /**
     * Gets the simulation setup.
     * @return the simulation setup
     */
    public SimulationSetup getSimulationSetup() {
        return simulationSetup;
    }

    public Session() {
    }

    /**
     * Constructor with parameters
     * @param trackableObjects list of object logs
     * @param sessionId unique id for user
     * @param currentDate the current date.
     */
    public Session(@JsonProperty("currentDate") LocalDateTime currentDate,
                   @JsonProperty("closeTrackableObjects") List<TrackableLog> trackableObjects,
                   @JsonProperty("sessionID") long sessionId,
                   @JsonProperty("adaptiveFeedbackLog") List<AdaptiveFeedback> adaptiveFeedbackLog,
                   @JsonProperty("simulationSetup") SimulationSetup simulationSetup) {

        this.currentDate = currentDate;

        checkIfObjectIsNull(trackableObjects, "trackable objects");
        this.trackableLogs = trackableObjects;

        checkIfNumberNotNegative(sessionId, "session ID");
        this.sessionId = sessionId;

        checkIfObjectIsNull(simulationSetup, "simulation setup");
        this.simulationSetup = simulationSetup;
    }

    /**
     * Get list of trackable objects
     * @return trackable objects
     */
    public List<TrackableLog> getTrackableObjects() {
        return trackableLogs;
    }

    /**
     * Set the trackable objects
     * @param trackableObjects list of trackable objects
     */
    public void setTrackableObjects(List<TrackableLog> trackableObjects) {
        this.trackableLogs = trackableObjects;
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
    public User getUser() {
        return user;
    }



    /**
     * Set user ID
     * @param user the user of this session.
     */
    public void setUser(User user) {
        this.user = user;
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



