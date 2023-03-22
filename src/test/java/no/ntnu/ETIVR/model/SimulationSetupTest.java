package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class SimulationSetupTest extends DefaultTest{

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
    }

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    /**
     * Makes a new default position configuration.
     * @return the position configuration.
     */
    private PositionConfiguration makePositionConfiguration(){
        List<CategoryConfiguration> categoryFeedbacks = new ArrayList<>();
        categoryFeedbacks.add(new CategoryConfiguration(TrackableType.OTHER, 0.5f));
        return new PositionConfiguration(categoryFeedbacks);
    }

    /**
     * Makes a default reference position.
     * @return the reference position.
     */
    private ReferencePosition makeReferencePosition(){
        return new ReferencePosition(50000, "Seat 1", makePositionConfiguration());
    }

    /**
     * Makes the default trackable objetcs.
     * @return the trackable objects.
     */
    private List<TrackableObject> makeTrackableObjects(){
        List<TrackableObject> trackableObjects = new ArrayList<>();
        trackableObjects.add(new TrackableObject("Pog", TrackableType.WALL, 5000));
        return trackableObjects;
    }

    /**
     * Makes default reference positions.
     * @return the default reference positions.
     */
    private List<ReferencePosition> makeReferencePostions(){
        List<ReferencePosition> referencePositions = new ArrayList<>();
        referencePositions.add(makeReferencePosition());
        return referencePositions;
    }

    /**
     * Tests if the constructor works with invalid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with invalid input.")
    public void testIfConstructorWorksWithInvalidInput(){
        String nameOfSetup = "Hei";
        List<TrackableObject> trackableObjects = makeTrackableObjects();
        List<ReferencePosition> referencePositions = makeReferencePostions();
        SimulationSetup simulationSetup;
        try {
            simulationSetup = new SimulationSetup(null, trackableObjects, referencePositions);
            addError(getIllegalPrefix(), "the input name is null");
        }catch (IllegalArgumentException exception){}
        try{
            simulationSetup = new SimulationSetup("", trackableObjects, referencePositions);
            addError(getIllegalPrefix(), "the input name is empty");
        }catch (IllegalArgumentException exception){}
        try {
            simulationSetup = new SimulationSetup(nameOfSetup, null, referencePositions);
            addError(getIllegalPrefix(), "the input trackable objects are null");
        }catch (IllegalArgumentException exception){}
        try {
            simulationSetup =  new SimulationSetup(nameOfSetup, trackableObjects, null);
            addError(getIllegalPrefix(), "the input refrence postions are null");
        }catch (IllegalArgumentException exception){}
    }

    /**
     * Tests if the constructor works with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input.")
    public void testIfConstructorWorksWithValidInput(){
        String nameOfSetup = "Hei";
        List<TrackableObject> trackableObjects = makeTrackableObjects();
        List<ReferencePosition> referencePositions = makeReferencePostions();
        SimulationSetup simulationSetup;
        try {
            simulationSetup = new SimulationSetup(nameOfSetup, trackableObjects, referencePositions);
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected the simluation setup to be made", "since the input is valid", exception);
        }

    }
}
