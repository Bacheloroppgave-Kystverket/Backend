package no.ntnu.ETIVR;

import lombok.SneakyThrows;
import no.ntnu.ETIVR.model.*;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.services.UserService;
import no.ntnu.ETIVR.model.trackable.GazeData;
import no.ntnu.ETIVR.model.trackable.TrackableRecord;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.trackable.ViewDistance;
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

        } catch (Exception couldNotAddTrackableObjectException){
            System.err.println("Test data could not be added but got an " + couldNotAddTrackableObjectException.getClass().getSimpleName() + ".");
            couldNotAddTrackableObjectException.printStackTrace();
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

    /**
     * Adds default test simulation setups.
     * @param simulationSetupService the setup simulation register.
     * @param trackableObjectRegister the trackable objects register.
     * @throws CouldNotAddSimulationSetupException gets thrown if the simulation setup could not be added.
     */
    private void addTestSimulationSetup(SimulationSetupService simulationSetupService, TrackableObjectRegister trackableObjectRegister) throws CouldNotAddSimulationSetupException {
        List<TrackableObject> trackableObjects = trackableObjectRegister.getAllTrackableObjects();
        List<ReferencePosition> referencePositions = makeReferencePositions();
        for (int i = 0; i < 1; i++){
            simulationSetupService.addSimulationSetup(new SimulationSetup("Tugboat " + i,trackableObjects ,referencePositions ));
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
                List<ReferencePosition> referencePositions = simulationSetup.getReferencePositions();
                Session session = new Session(LocalDateTime.now(), user, 500000 , makeTrackableLog(trackableObjects, referencePositions), makePositionLog(referencePositions, simulationSetup.getTrackableObjects()),
                    simulationSetup);
                session.setUser(user);
                sessionRegister.addSession(session);
            }
        }catch (CouldNotAddSessionException couldNotAddSessionException) {
            logger.warning("The defualt session could not be added.");
        }
    }

    private List<PositionRecord> makePositionLog(List<ReferencePosition> referencePositions, List<TrackableObject> trackableObjects){
        List<PositionRecord> positionData = referencePositions.stream().map(referencePosition -> new PositionRecord(referencePosition, 20, makeAdaptiveFeedback(referencePosition, trackableObjects))).toList();
        return positionData;
    }

    public PositionConfiguration makePositionConfiguration(){
        return new PositionConfiguration(makeCategoryFeedback());
    }

    /**
     * Makes a default set of reference positions.
     * @return the reference positions.
     */
    private List<ReferencePosition> makeReferencePositions(){
        List<ReferencePosition> referencePositions = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            referencePositions.add(new ReferencePosition(500000, "Seat " + i, makePositionConfiguration()));
        }
        return referencePositions;
    }

    /**
     * Makes the feedback configurations for the seats.
     * @return the feedback configurations
     */
    private List<CategoryConfiguration> makeCategoryFeedback(){
        List<CategoryConfiguration> categoryConfigurations = new ArrayList<>();
        for (TrackableType trackableType : TrackableType.values()){
            categoryConfigurations.add(new CategoryConfiguration(trackableType, 0.2f));
        }
        return categoryConfigurations;
    }

    /**
     * Makes default adaptive feedbacks.
     * @param referencePosition the reference position.
     * @param trackableObjects the trackable objects.
     * @return the adaptive feedback.
     */
    private List<AdaptiveFeedback> makeAdaptiveFeedback(ReferencePosition referencePosition, List<TrackableObject> trackableObjects){
        List<AdaptiveFeedback> adaptiveFeedbacks = new ArrayList<>();
        int time = 20;
        trackableObjects.forEach(trackableObject -> adaptiveFeedbacks.add(new AdaptiveFeedback(time, makeCategoryFeedback(time))));
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
    private List<TrackableRecord> makeTrackableLog(List<TrackableObject> trackableObjectList, List<ReferencePosition> referencePositions){
        List<TrackableRecord> trackableObjects = new ArrayList<>();
        referencePositions.forEach(referencePosition -> {
            trackableObjectList.forEach(trackableObject -> {
                List<GazeData> gazeDataList = new ArrayList<>();
                gazeDataList.add(new GazeData(1, 2, referencePosition));
                trackableObjects.add(new TrackableRecord(gazeDataList, ViewDistance.CLOSE, trackableObject));
            });
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
            trackableObjects.add(makeTrackableObject("Pog " + (i + 1)));
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
