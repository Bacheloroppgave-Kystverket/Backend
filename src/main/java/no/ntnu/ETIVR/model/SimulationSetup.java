package no.ntnu.ETIVR.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@NamedEntityGraph(
    name = "sessionEntityGraph",
    attributeNodes = {
        @NamedAttributeNode("simulationSetupId"),
        @NamedAttributeNode("nameOfSetup"),
        @NamedAttributeNode("closeTrackableObjects"),
        @NamedAttributeNode(value = "referencePositions", subgraph = "referencePositionGraph")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "referencePositionGraph",
            attributeNodes = {
                @NamedAttributeNode("locationName")
            }
        )
    }
)
@Entity(name = "simulationSetups")
public class SimulationSetup implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "simulationSetupId")
    private long simulationSetupId;

    @Column(unique = true)
    private String nameOfSetup;

    @ManyToMany(targetEntity = TrackableObject.class)
    @JoinTable(
        name = "closeSimulationObjects",
        joinColumns = @JoinColumn(name = "simulationSetupId", referencedColumnName = "simulationSetupId"),
        inverseJoinColumns = @JoinColumn(name = "trackableObjectID", referencedColumnName = "trackableObjectID")
    )
    private List<TrackableObject> closeTrackableObjects;

    //@OneToMany(cascade = CascadeType.ALL, targetEntity = ReferencePosition.class, fetch = FetchType.EAGER)
    //@JoinColumn(name = "sessionId")
    @Transient
    private List<ReferencePosition> referencePositions;

    /**
     * Makes an instance of the SimulationSetup class.
     */
    public SimulationSetup() {

    }

    /**
     * Makes an instance of the simulation setup.
     * @param nameOfSetup the name of the simulation setup.
     * @param trackableObjects the trackable objects.
     * @param referencePositions the reference positions.
     */
    public SimulationSetup(String nameOfSetup, List<TrackableObject> trackableObjects, List<ReferencePosition> referencePositions){
        checkIfObjectIsNull(trackableObjects, "trackable objects");
        checkIfObjectIsNull(referencePositions, "reference positions");
        checkString(nameOfSetup, "name of setup");
        this.nameOfSetup = nameOfSetup;
        this.closeTrackableObjects = trackableObjects;
        this.simulationSetupId = 500;
        this.referencePositions = referencePositions;
    }

    /**
     * Gets all the reference positions.
     * @return the reference positions.
     */
    @Fetch(FetchMode.SUBSELECT)
    public List<ReferencePosition> getReferencePositions(){
        return referencePositions;
    }

    /**
     * Gets the trackable objects.
     * @return the trackable objects.
     */
    public List<TrackableObject> getTrackableObjects(){
        return closeTrackableObjects;
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
