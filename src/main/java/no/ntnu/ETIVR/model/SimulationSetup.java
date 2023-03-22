package no.ntnu.ETIVR.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import java.util.List;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class SimulationSetup implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "simulationSetupId")
    private long simulationSetupId;

    @Column(unique = true)
    private String nameOfSetup;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "closeTrackableObjects",
        joinColumns = @JoinColumn(name = "simulationSetupId", referencedColumnName = "simulationSetupId"),
        inverseJoinColumns = @JoinColumn(name = "trackableObjectID", referencedColumnName = "trackableObjectID")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<TrackableObject> closeTrackableObjects;

    @OneToOne(targetEntity = PositionConfiguration.class, cascade = CascadeType.ALL)
    private PositionConfiguration positionConfiguration;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "simulationPositions",
        joinColumns = @JoinColumn(name = "simulationSetupId", referencedColumnName = "simulationSetupId"),
        inverseJoinColumns = @JoinColumn(name = "locationId", referencedColumnName = "locationId")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<ReferencePosition> referencePositionList;

    public List<TrackableObject> getCloseTrackableObjects() {
        return closeTrackableObjects;
    }

    /**
     * Makes an instance of the SimulationSetup class.
     */
    public SimulationSetup() {
        closeTrackableObjects =  new ArrayList<>();
    }

    /**
     * Makes an instance of the simulation setup.
     * @param nameOfSetup the name of the simulation setup.
     * @param trackableObjects the trackable objects.
     * @param referencePositions the reference positions.
     */
    public SimulationSetup(String nameOfSetup,
                           List<TrackableObject> trackableObjects,
                           List<ReferencePosition> referencePositions){
        checkIfObjectIsNull(trackableObjects, "trackable objects");
        checkIfObjectIsNull(referencePositions, "reference positions");
        checkString(nameOfSetup, "name of setup");
        this.nameOfSetup = nameOfSetup;
        this.closeTrackableObjects = trackableObjects;
        this.simulationSetupId = 500;
        this.positionConfiguration = positionConfiguration;
        this.referencePositionList = referencePositions;
    }

    /**
     * Gets all the reference positions.
     * @return the reference positions.
     */
    public List<ReferencePosition> getReferencePositions(){
        return this.referencePositionList;
    }

    /**
     * Gets the position configuration.
     * @return the position configuration.
     */
    public PositionConfiguration getPositionConfiguration(){
        return positionConfiguration;
    }

    /**
     * Gets the trackable objects.
     * @return the trackable objects.
     */
    public List<TrackableObject> getTrackableObjects(){
        return closeTrackableObjects.stream().toList();
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
