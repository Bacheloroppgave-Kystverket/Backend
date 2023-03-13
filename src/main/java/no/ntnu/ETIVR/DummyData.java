package no.ntnu.ETIVR;

import lombok.SneakyThrows;
import no.ntnu.ETIVR.model.*;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.repository.TrackableObjectRepository;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
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

    public DummyData(TrackableObjectsService trackableObjectsService, SessionService sessionService){
        this.trackableObjectsService = trackableObjectsService;
        this.sessionRegister = sessionService;
        try {
            addTestTrackableObject(trackableObjectsService);
            addTestSession();
        } catch (CouldNotAddTrackableObjectException couldNotAddTrackableObjectException){
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
     * Adds test sessions to the database.
     */
    private void addTestSession(){
        try {
            for (int i = 0; i < 4; i++){
                List<ReferencePosition> referencePositions =  makeReferencePositions();
                List<TrackableObject> trackableObjects = makeTrackableObjects();
                sessionRegister.addSession(new Session(LocalDateTime.now(), 1, trackableObjects, 500000, referencePositions, makeAdaptiveFeedback(referencePositions, trackableObjects)));
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
            referencePositions.add(new ReferencePosition(500000, "Seat " + i, 1200, makeFeedbackConfigurations()));
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
        referencePositions.forEach(positon -> adaptiveFeedbacks.add(new AdaptiveFeedback(positon.getLocationName(), time, makeCategoryFeedback(time))));
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
    private List<TrackableObject> makeTrackableObjects(){
        List<TrackableObject> trackableObjects = new ArrayList<>();
        trackableObjects.add(new TrackableObject("Name Object", TrackableType.WALL, ViewDistance.FAR, 500000));
        trackableObjects.add(new TrackableObject("Name Object1", TrackableType.WALL, ViewDistance.FAR, 500000));
        trackableObjects.add(new TrackableObject("Name Object2", TrackableType.WALL, ViewDistance.FAR, 500000));
        trackableObjects.add(new TrackableObject("Name Object3", TrackableType.WALL, ViewDistance.FAR, 500000));
        return trackableObjects;
    }

    /**
     * Adding trackable object test data
     * @param trackableObjectRegister Track
     * @throws CouldNotAddTrackableObjectException
     */
    public void addTestTrackableObject(TrackableObjectRegister trackableObjectRegister) throws CouldNotAddTrackableObjectException {
        checkIfObjectIsNull(trackableObjectRegister);

        if (trackableObjectRegister.getAllTrackableObjects().isEmpty()) {
            List<TrackableObject> trackableObjects = makeTrackableObjects();
            for (TrackableObject trackableObject : trackableObjects) {
                trackableObjectRegister.addTrackableObject(trackableObject);
            }
        }
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
