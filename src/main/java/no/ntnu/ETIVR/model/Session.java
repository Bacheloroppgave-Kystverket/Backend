package no.ntnu.ETIVR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import java.time.chrono.ChronoLocalDate;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.trackable.TrackableRecord;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @OneToMany(cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
        name = "trackableRecords",
        joinColumns = @JoinColumn(name = "sessionId", referencedColumnName = "sessionId"),
        inverseJoinColumns = @JoinColumn(name = "trackableDataId", referencedColumnName = "trackableDataId")
    )
    private List<TrackableRecord> trackableRecordList;


    @OneToMany(cascade = {CascadeType.ALL})
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
        name = "positionRecords",
        joinColumns = @JoinColumn(name = "sessionId", referencedColumnName = "sessionId"),
        inverseJoinColumns = @JoinColumn(name = "posDataId", referencedColumnName = "posDataId")
    )
    private List<PositionRecord> positionRecords;

    @OneToOne
    @JoinColumn(name = "simulationSetupId")
    private SimulationSetup simulationSetup;

    public Session() {

    }

    /**
     * Makes an instance of the Session class.
     * @param currentDate the current date.
     * @param user the user.
     * @param sessionId the id of the session
     * @param trackableRecords trackable log.
     * @param positionRecords the position log.
     * @param simulationSetup the simulation setup.
     */
    public Session(@JsonProperty("currentDate") LocalDateTime currentDate,
                   @JsonProperty("user") User user,
                   @JsonProperty("sessionID") long sessionId,
                   @JsonProperty("trackableRecords") List<TrackableRecord> trackableRecords,
                   @JsonProperty("positionRecords") List<PositionRecord> positionRecords,
                   @JsonProperty("simulationSetup") SimulationSetup simulationSetup) {
        checkIfObjectIsNull(currentDate, "current date");
        this.currentDate = currentDate;
        checkIfObjectIsNull(user, "user");
        this.user = user;
        checkIfNumberNotNegative(sessionId, "session ID");
        this.sessionId = sessionId;
        checkIfObjectIsNull(simulationSetup, "simulation setup");
        this.simulationSetup = simulationSetup;
        checkIfObjectIsNull(trackableRecords, "trakcable records");
        this.trackableRecordList = trackableRecords;
        checkIfObjectIsNull(positionRecords, "position records");
        this.positionRecords = positionRecords;
    }

    /**
     * Gets the simulation setup.
     * @return the simulation setup
     */
    public SimulationSetup getSimulationSetup() {
        return simulationSetup;
    }


    /**
     * Get session id
     * @return int session id
     */
    public long getSessionId() {
        return sessionId;
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
        checkIfObjectIsNull(currentDate, "current date");
        this.currentDate = currentDate;
    }

    /**
     * Set user ID
     * @param user the user of this session.
     */
    public void setUser(User user) {
        checkIfObjectIsNull(user, "user");
        this.user = user;
    }

    /**
     * Get user ID
     * @return user id
     */
    public User getUser() {
        return user;
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



