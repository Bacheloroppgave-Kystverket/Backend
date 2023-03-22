package no.ntnu.ETIVR.model;

import javax.swing.text.Position;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.GazeData;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableRecord;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.trackable.ViewDistance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class SessionTest extends DefaultTest{

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
     * Makes a default user.
     * @return the default user.
     */
    private User makeUser(){
        return new User(1, "hei", "pass");
    }

    /**
     * Makes a default simulation setup.
     * @return the simulation setup.
     */
    private SimulationSetup makeSimulationSetup(){
        List<TrackableObject> trackableObjects = new ArrayList<>();

        trackableObjects.add(new TrackableObject("Pog", TrackableType.WALL, 5000));
        List<ReferencePosition> referencePositions = new ArrayList<>();
        referencePositions.add(makeReferencePosition());
        return new SimulationSetup("Hei", trackableObjects, referencePositions);
    }

    /**
     * Makes trackable records.
     * @param simulationSetup the simulation setup.
     * @return the trackable records.
     */
    private List<TrackableRecord> makeTrackableRecords(SimulationSetup simulationSetup){
        List<GazeData> gazeData = new ArrayList<>();
        gazeData.add(new GazeData(1,1,simulationSetup.getReferencePositions().get(0)));
        return simulationSetup.getCloseTrackableObjects().stream().map(trackableObject -> new TrackableRecord(gazeData, ViewDistance.CLOSE, trackableObject)).toList();
    }

    /**
     * Makes a default position records.
     * @param simulationSetup the simulation setup.
     * @return the list of the simulation setups.
     */
    private List<PositionRecord> makePositionRecords(SimulationSetup simulationSetup){
        ReferencePosition referencePosition = simulationSetup.getReferencePositions().get(0);
        List<PositionRecord> positionRecords = new ArrayList<>();
        positionRecords.add(new PositionRecord(referencePosition,1,  makeAdaptiveFeedback(referencePosition)));
        return positionRecords;
    }

    /**
     * Makes the adaptive feedbacks.
     * @param referencePosition the refernce position.
     * @return the list of adaptive feedbacks.
     */
    private List<AdaptiveFeedback> makeAdaptiveFeedback(ReferencePosition referencePosition){
        List<AdaptiveFeedback> adaptiveFeedbacks = new ArrayList<>();
        List<CategoryFeedback> categoryFeedbacks = new ArrayList<>();
        categoryFeedbacks.add(new CategoryFeedback(TrackableType.OTHER, 1));
        adaptiveFeedbacks.add(new AdaptiveFeedback(1, categoryFeedbacks));
        return adaptiveFeedbacks;
    }

    /**
     * Makes a default session
     * @return the default session.
     */
    private Session makeDefaultSession() {
        SimulationSetup simulationSetup = makeSimulationSetup();
        return new Session(LocalDateTime.now(), makeUser() ,500000, makeTrackableRecords(simulationSetup), makePositionRecords(simulationSetup),simulationSetup);
    }

    @Test
    @DisplayName("Tests if the constructor works with valid input")
    public void testConstructorValidInput() {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = makeUser();
        long sessionId = 500000;
        SimulationSetup simulationSetup = makeSimulationSetup();
        List<TrackableRecord> trackableRecords = makeTrackableRecords(simulationSetup);
        List<PositionRecord> positionRecords = makePositionRecords(simulationSetup);

        try{
            Session session = new Session(localDateTime, user, sessionId, trackableRecords, positionRecords, simulationSetup);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Expected the session", "to made", e);
        }
    }



    @Test
    @DisplayName("Tests if the constructor works with invalid input")
    public void testConstructorInvalidInput() {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = makeUser();
        long sessionId = 500000;
        SimulationSetup simulationSetup = makeSimulationSetup();
        List<TrackableRecord> trackableRecords = makeTrackableRecords(simulationSetup);
        List<PositionRecord> positionRecords = makePositionRecords(simulationSetup);


        Session session;

        try{
            session = new Session(null, user, sessionId, trackableRecords, positionRecords, simulationSetup);
            addError(getIllegalPrefix(), "the current date is null");
        } catch (IllegalArgumentException e) {}
        try {
            session = new Session(localDateTime, null, sessionId, trackableRecords, positionRecords, simulationSetup);
            addError(getIllegalPrefix(), "the user is null");
        }catch (IllegalArgumentException exception){}
        try {
            session = new Session(localDateTime, user, -500, trackableRecords, positionRecords, simulationSetup);
            addError(getIllegalPrefix(), "the session id is negative");
        }catch (IllegalArgumentException exception){}
        try {
            session = new Session(localDateTime, user, sessionId, null, positionRecords, simulationSetup);
            addError(getIllegalPrefix(), "the trackable records are null");
        }catch (IllegalArgumentException exception){}
        try {
            session = new Session(localDateTime, user, sessionId, trackableRecords, null, simulationSetup);
            addError(getIllegalPrefix(), "the position records are null");
        }catch (IllegalArgumentException exception){}
        try {
            session = new Session(localDateTime, user, sessionId, trackableRecords, positionRecords, null);
            addError(getIllegalPrefix(), "the simulation setup is null");
        }catch (IllegalArgumentException exception){}
    }

    /**
     * Tests if setUser works with invalid input.
     */
    @Test
    @DisplayName("Tests if setUser works with invalid input.")
    public void testIfSetUserWorksWithInvalidInput(){
        Session session = makeDefaultSession();
        try {
            session.setUser(null);
            addError(getIllegalPrefix(), "the input user is null");
        }catch (IllegalArgumentException exception){}
    }

    /**
     * Tests if setUser works with valid input.
     */
    @Test
    @DisplayName("Tests if setUser works with valid input.")
    public void testIfSetUserWorksWithValidInput(){
        Session session = makeDefaultSession();
        try {
            session.setUser(makeUser());
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected the user to be made", "since the input is valid", exception);
        }
    }

    /**
     * Tests if setCurrentDate works with invalid input.
     */
    @Test
    @DisplayName("Tests if setCurrentDate works with invalid input.")
    public void testIfSetCurrentDateWorksWithInvalidInput(){
        Session session = makeDefaultSession();
        try {
            session.setCurrentDate(null);
            addError(getIllegalPrefix(), "the input date is null");
        }catch (IllegalArgumentException exception){}
    }

    /**
     * Tests if setCurrentDate works with valid input.
     */
    @Test
    @DisplayName("Tests if setCurrentDate works with valid input.")
    public void testIfSetCurrentDateWorksWithValidInput(){
        Session session = makeDefaultSession();
        try {
            session.setCurrentDate(LocalDateTime.now());
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected to get a object", "since the input is valid", exception);
        }
    }

}
