package no.ntnu.ETIVR.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class SimulationSetup {

    @Id
    @GeneratedValue
    private long simulationSetupId;

    @Column(unique = true)
    private String nameOfSetup;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = TrackableObject.class)
    @JoinColumn(name = "sessionId")
    private List<TrackableObject> closeTrackableObjects = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ReferencePosition.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "sessionId")
    private List<ReferencePosition> referencePositions;

    /**
     * Makes an instance of the SimulationSetup class.
     */
    public SimulationSetup() {

    }

    /**
     * Gets the simulation setup id.
     * @return the id.
     */
    public long getSimulationSetupId(){
        return simulationSetupId;
    }

    /**
     * Checks if a string is of a valid format or not.
     *
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
}
