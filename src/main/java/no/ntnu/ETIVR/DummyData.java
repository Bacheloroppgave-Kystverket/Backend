package no.ntnu.ETIVR;

import lombok.SneakyThrows;
import no.ntnu.ETIVR.model.*;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.services.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Profile("!test")
public class DummyData implements ApplicationListener<ApplicationReadyEvent> {

    private TrackableObjectsService trackableObjectsService;

    private final SessionRegister sessionRegister;

    private final Logger logger = Logger.getLogger("DummyInit");

    public DummyData(TrackableObjectsService trackableObjectsService, SessionService sessionService, SimulationSetupService simulationSetupService, UserService userRegister){
        this.trackableObjectsService = trackableObjectsService;
        this.sessionRegister = sessionService;
        try {
            addTrackableObjects(trackableObjectsService);
            addTestSimulationSetup(simulationSetupService, trackableObjectsService);
            addDefaultUsers(userRegister);
            addTestSession(simulationSetupService, userRegister);
            //addTrackableObjects(trackableObjectsService);

        } catch (Exception couldNotAddTrackableObjectException){
            System.err.println("Test data could not be added but got an " + couldNotAddTrackableObjectException.getClass().getSimpleName() + ".");
        }
    }

    @Override
    @SneakyThrows
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!trackableObjectsService.getAllTrackableObjects().isEmpty()) {
            logger.info("Data already exists");
        } else {
            logger.info("importing test data...");
        }
    }

    /**
     * Adds default users to the backend.
     * @param userRegister the user register.
     */
    private void addDefaultUsers(UserRegister userRegister) throws CouldNotAddUserException {
        for (int i = 0; i < 5; i++){
            User user = new User(5000, "Hei " + i, "pass");
            userRegister.addNewUser(user);
        }
    }

    private List<User> makeDefaultUsers(){
        return null;
    }

    /**
     * Adds default test simulation setups.
     * @param simulationSetupService the setup simulation register.
     * @param trackableObjectRegister the trackable objects register.
     * @throws CouldNotAddSimulationSetupException gets thrown if the simulation setup could not be added.
     */
    private void addTestSimulationSetup(SimulationSetupService simulationSetupService, TrackableObjectRegister trackableObjectRegister) throws CouldNotAddSimulationSetupException {
        List<TrackableObject> trackableObjects = trackableObjectRegister.getAllTrackableObjects();List<ReferencePosition> referencePositions = makeReferencePositions();
        for (int i = 0; i < 5; i++){
            simulationSetupService.addSimulationSetup(new SimulationSetup("Tugboat " + i,trackableObjects, referencePositions));
        }
    }

    /**
     * Adds test sessions to the database.
     * @param simulationSetupRegister the simulation setup register.
     */
    private void addTestSession(SimulationSetupRegister simulationSetupRegister, UserRegister userRegister){
        try {
            SimulationSetup simulationSetup = simulationSetupRegister.getSimulationSetups().get(0);
            User user = userRegister.getAllUsers().get(0);
            for (int i = 0; i < 4; i++){
                List<TrackableObject> trackableObjects = simulationSetup.getTrackableObjects();
                Session session = new Session(LocalDateTime.now(), makeTrackableLogs(trackableObjects) , 500000, makeAdaptiveFeedback(simulationSetup.getReferencePositions(), trackableObjects), simulationSetup);
                session.setUser(user);
                sessionRegister.addSession(session);
            }
        }catch (CouldNotAddSessionException couldNotAddSessionException) {
            logger.warning("The defualt session could not be added.");
        }
    }

    /**
     * Makes a default set of reference positions.
     * @return the reference positions.
     */
    private List<ReferencePosition> makeReferencePositions(){
        List<ReferencePosition> referencePositions = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            referencePositions.add(new ReferencePosition(500000, "Seat " + i));
        }
        return referencePositions;
    }

    /**
     * Makes the feedback configurations for the seats.
     * @return the feedback configurations
     */
    private List<FeedbackConfiguration> makeFeedbackConfigurations(){
        List<FeedbackConfiguration> feedbackConfigurations = new ArrayList<>();
        for (TrackableType trackableType : TrackableType.values()){
            feedbackConfigurations.add(new FeedbackConfiguration(trackableType, 0.2f));
        }
        return feedbackConfigurations;
    }

    /**
     * Makes default adaptive feedbacks.
     * @param referencePositions the reference positions.
     * @param trackableObjects the trackable objects.
     * @return the adaptive feedback.
     */
    private List<AdaptiveFeedback> makeAdaptiveFeedback(List<ReferencePosition> referencePositions, List<TrackableObject> trackableObjects){
        List<AdaptiveFeedback> adaptiveFeedbacks = new ArrayList<>();
        int time = 20;
        referencePositions.forEach(positon -> trackableObjects.forEach(trackableObject -> adaptiveFeedbacks.add(new AdaptiveFeedback(time, makeCategoryFeedback(time), positon.getLocationName()))));
        return adaptiveFeedbacks;
    }

    /**
     * Makes default category feedbacks.
     * @param time the total time.
     * @return the list of the category feedbacks
     */
    private List<CategoryFeedback> makeCategoryFeedback(int time){
        List<CategoryFeedback> categoryFeedbacks = new ArrayList<>();
        int amount = TrackableType.values().length - 1;
        int dividedTime = time/amount;
        for (TrackableType trackableType : TrackableType.values()){
            categoryFeedbacks.add(new CategoryFeedback(trackableType, dividedTime));
        }
        return categoryFeedbacks;
    }

    /**
     * Makes a set of predefined trackable objects.
     * @return the trackable objects
     */
    private List<TrackableLog> makeTrackableLogs(List<TrackableObject> trackableObjectList){
        List<TrackableLog> trackableObjects = new ArrayList<>();
        trackableObjectList.forEach(trackableObject -> {
            trackableObjects.add(new TrackableLog(new ArrayList<>(), ViewDistance.CLOSE, trackableObject));
        });
        return trackableObjects;
    }

    /**
     * Makes a default trackable object based on the name.
     * @param nameOfObject the name of the object.
     * @return the trackable object.
     */
    private TrackableObject makeTrackableObject(String nameOfObject){
        return new TrackableObject(nameOfObject, TrackableType.WALL, 500000);
    }

    /**
     * Adding trackable logs test data
     * @param trackableObjectRegister Track
     * @throws CouldNotAddTrackableObjectException
     */
    public void addTrackableObjects(TrackableObjectRegister trackableObjectRegister) throws CouldNotAddTrackableObjectException {
        checkIfObjectIsNull(trackableObjectRegister);

        if (trackableObjectRegister.getAllTrackableObjects().isEmpty()) {
            for (TrackableObject trackableObject: makeDefaultTrackableObjects()){
                trackableObjectRegister.addTrackableObject(trackableObject);
            }
        }
    }

    public List<TrackableObject> makeDefaultTrackableObjects(){
        List<TrackableObject> trackableObjects = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            trackableObjects.add(makeTrackableObject("Pog " + i));
        }
        return trackableObjects;
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("The " + "trackable object register" + " cannot be null.");
        }
    }

}
