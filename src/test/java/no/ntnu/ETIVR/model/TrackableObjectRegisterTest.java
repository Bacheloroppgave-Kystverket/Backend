package no.ntnu.ETIVR.model;

import static org.junit.jupiter.api.Assertions.fail;


import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Tests the trackable objects register.
 */
@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TrackableObjectRegisterTest extends DefaultTest implements RegisterTest{

    private TrackableObjectRegister trackableObjectRegister;

    private TrackableObject trackableObjectInRegister;

    private String removeException;

    private String addException;

    private String getException;

    /**
     * Makes an instance of the trackable object register test and tests the class.
     * @param trackableObjectsService the trackable objects register.
     */
    @Autowired
    public TrackableObjectRegisterTest(TrackableObjectsService trackableObjectsService){
        super();
        this.trackableObjectRegister = trackableObjectsService;
        checkIfObjectIsNull(trackableObjectsService, "Trackable objects regeister");
        removeException = "makeExceptionString()";
    }

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
        try {
            for(TrackableObject trackableObject : trackableObjectRegister.getAllTrackableObjects()){
                trackableObjectRegister.removeTrackableObjectWithID(trackableObject.getTrackableObjectID());
            }
            trackableObjectRegister.addTrackableObject(makeDefaultTrackableObject());
            trackableObjectInRegister = trackableObjectRegister.getAllTrackableObjects().get(0);
        }catch (IllegalArgumentException | CouldNotRemoveTrackableObjectException | CouldNotAddTrackableObjectException exception){
            fail(makeCouldNotGetDefaultString("test data"));
            exception.printStackTrace();
        }
    }

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }

    /**
     * Makes a default trackable object.
     * @return the default trackable object.
     */
    private TrackableObject makeDefaultTrackableObject(){
        return new TrackableObject("hei", TrackableType.CUBE, ViewDistance.CLOSE, 5000);
    }

    @Override
    @AfterAll
    public void cleanUp() {
        try{
            List<TrackableObject> trackableObjectList = trackableObjectRegister.getAllTrackableObjects();
            for (TrackableObject trackableObject : trackableObjectList){
                trackableObjectRegister.removeTrackableObject(trackableObject);
            }
        }catch (CouldNotRemoveTrackableObjectException | IllegalArgumentException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Tests if add trackable object works with valid input.
     */
    @DisplayName("Tests if add trackable object works with valid input.")
    @Test
    public void testIfAddTrackableObjectWorksWithValidInput(){
        try{
            trackableObjectRegister.addTrackableObject(makeDefaultTrackableObject());
        }catch (IllegalArgumentException | CouldNotAddTrackableObjectException exception){
            addErrorWithException("Expected the trackable object to be added", "since the input is valid", exception);
        }
    }

    /**
     * Tests if a remove trackable object works with invalid input
     */
    @DisplayName("Tests if a remove trackable object works with invalid input")
    @Test
    public void testIfAddTrackableObjectWorksWithInValidInput(){
        try {
            trackableObjectRegister.addTrackableObject(null);
            addError(getIllegalPrefix(), "the input is null");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotAddTrackableObjectException exception){
            addErrorWithException(getIllegalPrefix(), "the input is null", exception);
        }
        try{
            trackableObjectRegister.addTrackableObject(trackableObjectInRegister);
        }catch (IllegalArgumentException exception){
            addErrorWithException(addException, "input is already in the register", exception);
        }catch (CouldNotAddTrackableObjectException exception){
        }
    }

    /**
     * Tests if remove trackable object works with valid input.
     */
    @Test
    @DisplayName("Tests if remove trackable object works with valid input.")
    public void testIfRemoveTrackableObjectWorksWithValidInput(){
        try {
            trackableObjectRegister.removeTrackableObject(trackableObjectInRegister);
        } catch (CouldNotRemoveTrackableObjectException | IllegalArgumentException e) {
            addErrorWithException("Expected the trackable object to be removed", "since the input is valid", e);
        }
    }

    /**
     * Tests if remove trackable object works with invalid input.
     */
    @Test
    @DisplayName("Tests if remove trackable objecct works with invalid input")
    public void testIfRemoveTrackableObjectWorksWithInvalidInput(){
        try {
            trackableObjectRegister.removeTrackableObject(null);
            addError(getIllegalPrefix(), "the input is null");
        }catch (IllegalArgumentException exception){}
        catch (CouldNotRemoveTrackableObjectException exception){
            addErrorWithException(getIllegalPrefix(), "the input is null", exception);
        }
        try {
            trackableObjectRegister.removeTrackableObject(makeDefaultTrackableObject());
            addError(getIllegalPrefix(), "the input object is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(removeException, "the input object is not in the register", exception);
        }catch (CouldNotRemoveTrackableObjectException exception){}
    }

    /**
     * Tests if remove trackable object with ID works with valid input.
     */
    @Test
    @DisplayName("Tests if remove trackable object with ID works with valid input.")
    public void testIfRemoveTrackableObjectWithIdWorksWithValidInput(){
        try {
            trackableObjectRegister.removeTrackableObjectWithID(trackableObjectInRegister.getTrackableObjectID());
        }catch (IllegalArgumentException | CouldNotRemoveTrackableObjectException exception ){
            addErrorWithException("Expected the object to be removed", "since its in the register", exception);
        }
    }

    /**
     * Tests if remove trackable object with ID works with invalid input.
     */
    @Test
    @DisplayName("Tests if remove trackable object with ID works with invalid input.")
    public void testIfRemoveTrackableObjectWithIDWorksWithInvalidInput(){
        try {
            trackableObjectRegister.removeTrackableObjectWithID(-2);
            addError(getIllegalPrefix(), "the input is negative");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotRemoveTrackableObjectException exception){
            addErrorWithException(getIllegalPrefix(), "the input is negative", exception);
        }
        try {
            trackableObjectRegister.removeTrackableObjectWithID(60000);
            addError(removeException, "the input id is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(removeException, "the input id is not in the register", exception);
        }catch (CouldNotRemoveTrackableObjectException exception){}
    }

    /**
     * Tests if get trackable object with id works with valid input.
     */
    @Test
    @DisplayName("Tests if get trackable object with id works with valid input.")
    public void testIfGetTrackableObjectByIDWorksWithValidInput(){
        try {
            trackableObjectRegister.getTrackableObjectById(trackableObjectInRegister.getTrackableObjectID());
        }catch (IllegalArgumentException | CouldNotGetTrackableObjectException exception){
            addErrorWithException("Expected the trackable object to be recived since", "the input is valid", exception);
        }
    }

    /**
     * Tests if get trackable object works with invalid input.
     */
    @Test
    @DisplayName("Tests if get trackable object works with invalid input.")
    public void testIfGetTrackableObjectWorksWithInvalidInput(){
        try {
            trackableObjectRegister.getTrackableObjectById(-2);
            addError(getIllegalPrefix(), "the input is negative");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotGetTrackableObjectException exception){
            addErrorWithException(getIllegalPrefix(), "the input is negative", exception);
        }
        try {
            trackableObjectRegister.getTrackableObjectById(50000);
            addError(getException, "the input is not in the register");
        }catch (IllegalArgumentException exception){
            addErrorWithException(getException, "the input is not in the register", exception);
        }catch (CouldNotGetTrackableObjectException exception){

        }
    }





}
