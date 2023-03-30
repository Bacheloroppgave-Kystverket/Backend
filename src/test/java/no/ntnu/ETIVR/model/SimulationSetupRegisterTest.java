package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.*;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.trackable.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimulationSetupRegisterTest extends DefaultTest implements RegisterTest{

    private String removeException;

    private String addException;

    private String getException;

    private SimulationSetupRegister simulationSetupRegister;

    private TrackableObjectRegister trackableObjectRegister;

    private SimulationSetup simulationSetup;

    /**
     * Makes an instance of the SimulationSetupRegisterTest.
     * @param simulationSetupService the simulation setup service.
     */
    @Autowired
    public SimulationSetupRegisterTest(SimulationSetupService simulationSetupService, TrackableObjectsService trackableObjectsService) throws CouldNotAddTrackableObjectException {
        super();
        this.simulationSetupRegister = simulationSetupService;
        removeException = makeExceptionString(CouldNotRemoveSimulationSetupException.class.getSimpleName());
        addException = makeExceptionString(CouldNotAddSimulationSetupException.class.getSimpleName());
        getException = makeExceptionString(CouldNotGetSimulationSetupException.class.getSimpleName());
        this.trackableObjectRegister = trackableObjectsService;
        this.trackableObjectRegister.addTrackableObject(new TrackableObject("Pog", TrackableType.WALL, 5000));
    }

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
        try {
            for (SimulationSetup simulationSetup : simulationSetupRegister.getSimulationSetups()){
                simulationSetupRegister.removeSimulationSetup(simulationSetup);
            }
            simulationSetupRegister.addSimulationSetup(makeSimulationSetup("Hei"));
            this.simulationSetup = simulationSetupRegister.getSimulationSetups().get(0);
        }catch (IllegalArgumentException | CouldNotRemoveSimulationSetupException | CouldNotAddSimulationSetupException exception){
            fail(makeCouldNotGetDefaultString("simulation setup"));
            exception.printStackTrace();
        }
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
     * Makes a default simulation setup.
     * @return the simulation setup.
     */
    private SimulationSetup makeSimulationSetup(String name){
        List<TrackableObject> trackableObjects = trackableObjectRegister.getAllTrackableObjects();
        List<ReferencePosition> referencePositions = new ArrayList<>();

        referencePositions.add(makeReferencePosition());
        return new SimulationSetup(name, trackableObjects, referencePositions);
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

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    @Override
    @AfterAll
    public void cleanUp() {
        try{
            List<SimulationSetup> simulationSetups = this.simulationSetupRegister.getSimulationSetups();
            for (SimulationSetup simulationSetup : simulationSetups){
                this.simulationSetupRegister.removeSimulationSetup(simulationSetup);
            }
            for (TrackableObject trackableObject : trackableObjectRegister.getAllTrackableObjects()){
                trackableObjectRegister.removeTrackableObject(trackableObject);
            }
        }catch (IllegalArgumentException | CouldNotRemoveSimulationSetupException | CouldNotRemoveTrackableObjectException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Tests if addSimulationSetup works with invalid input.
     */
    @Test
    @DisplayName("Tests if addSimulationSetup works with invalid input.")
    public void testIfAddSimulationSetupWorksWithInvalidInput(){
        try {
            this.simulationSetupRegister.addSimulationSetup(null);
            addError(getIllegalPrefix(), "the input simulation setup is null");
        }catch (IllegalArgumentException exception){}
        catch (CouldNotAddSimulationSetupException exception){
            addErrorWithException(getIllegalPrefix(), "the input simulation setup is null", exception);
        }
        try {
            this.simulationSetupRegister.addSimulationSetup(simulationSetup);
            addError(addException, "the input simulation setup is already in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(addException, "the input simulation setup is already in the register", exception);
        }catch (CouldNotAddSimulationSetupException exception){}
    }

    /**
     * Tests if addSimulationSetup works with valid input.
     */
    @Test
    @DisplayName("Tests if addSimulationSetup works with valid input.")
    public void testIfAddSimulationSetupWorksWithValidInput(){
        try {
            this.simulationSetupRegister.addSimulationSetup(makeSimulationSetup("hello"));
        }catch (IllegalArgumentException | CouldNotAddSimulationSetupException exception){
            addErrorWithException("Expected the simulation setup to be added since", "the input is valid", exception);
        }
    }

    /**
     * Tests if removeSimulationSetup works with invalid input.
     */
    @Test
    @DisplayName("Tests if removeSimulationSetup works with invalid input.")
    public void testIfRemoveSimulationSetupWorksWithInvalidInput(){
        try {
            this.simulationSetupRegister.removeSimulationSetup(null);
            addError(getIllegalPrefix(), "the input is null");
        }catch (IllegalArgumentException exceptione){

        }catch (CouldNotRemoveSimulationSetupException exception){
            addErrorWithException(getIllegalPrefix(), "the input is null", exception);
        }
        try {
            this.simulationSetupRegister.removeSimulationSetup(makeSimulationSetup("hei"));
            addError(removeException, "the input simulation setup is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(removeException, "the input simulation setup is not in the register", exception);
        }catch (CouldNotRemoveSimulationSetupException exception){

        }
    }


    /**
     * Tests if removeSimulationSetup works with valid input.
     */
    @Test
    @DisplayName("Tests if removeSimulationSetup works with valid input.")
    public void testIfRemoveSimulationSetupWorksWithValidInput(){
        try {
            this.simulationSetupRegister.removeSimulationSetup(simulationSetup);
        }catch (IllegalArgumentException | CouldNotRemoveSimulationSetupException exception){
            addErrorWithException("Expected the simulation setup to be removed since", "the input is valid", exception);
        }
    }

    /**
     * Tests if getSimulationSetupById works with valid input.
     */
    @Test
    @DisplayName("Tests if getSimulationSetupById works with valid input.")
    public void testIfRemoveSetupWithIdWorksWithValidInput(){
        try {
            simulationSetupRegister.getSimulationSetupById(simulationSetup.getSimulationSetupId());
        }catch (IllegalArgumentException | CouldNotGetSimulationSetupException exception){
            addErrorWithException("Expected the simulation setup to be found", "since the input is valid", exception);
        }
    }


    /**
     * Tests if getSimulationSetupById works with invalid input.
     */
    @Test
    @DisplayName("Tests if removeSimulationSetup works with invalid input.")
    public void testIfRemoveSetupWithIdWorksWithInvalidInput(){
        try {
            this.simulationSetupRegister.getSimulationSetupById(-2);
            addError(getIllegalPrefix(), "the input is negative");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotGetSimulationSetupException exception){
            addErrorWithException(getIllegalPrefix(), "the input is negative", exception);
        }
        try {
            this.simulationSetupRegister.getSimulationSetupById(5000000);
            addError(getException, "the input simulation id is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(getException, "the input simulation id is not in the register", exception);
        }catch (CouldNotGetSimulationSetupException exception){}
    }

    /**
     * Tests if getSimulationSetupBySetupName works with invalid input.
     */
    @Test
    @DisplayName("ests if getSimulationSetupBySetupName works with invalid input.")
    public void testIfGetSimulationSetupBySetupNameWorksWithInvalidInput(){
        try {
            simulationSetupRegister.getSimulationSetupByName("");
            addError(getIllegalPrefix(), "the input setup name is empty");
        }catch (IllegalArgumentException exception){}
        catch (CouldNotGetSimulationSetupException exception){
            addErrorWithException(getIllegalPrefix(), "the input setup name is empty", exception);
        }
        try{
            simulationSetupRegister.getSimulationSetupByName(null);
            addError(getIllegalPrefix(), "the input setup name is null");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotGetSimulationSetupException exception){
            addErrorWithException(getIllegalPrefix(), "the input setup name is null", exception);
        }
        try {
            simulationSetupRegister.getSimulationSetupByName("HEllloooo");
            addError(getException, "the input setup name is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(getException, "the input setup name is not in the register", exception);
        }catch (CouldNotGetSimulationSetupException exception){}
    }

    /**
     * Tests if getSimulationSetupBySetupName works with valid input.
     */
    @Test
    @DisplayName("Tests if getSimulationSetupBySetupName works with valid input.")
    public void testIfGetSimulationBySetupNameWorksWithValidInput(){
        try {
            simulationSetupRegister.getSimulationSetupByName(simulationSetup.getNameOfSetup());
        }catch (IllegalArgumentException | CouldNotGetSimulationSetupException exception){
            addErrorWithException("Expected the simulation setup to be made since the input is valid", "", exception);
        }
    }
}
