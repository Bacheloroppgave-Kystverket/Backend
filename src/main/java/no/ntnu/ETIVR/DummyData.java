package no.ntnu.ETIVR;

import lombok.SneakyThrows;
import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.TrackableType;
import no.ntnu.ETIVR.model.ViewDistance;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.repository.TrackableObjectRepository;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Profile("!test")
public class DummyData implements ApplicationListener<ApplicationReadyEvent> {
    private TrackableObjectsService trackableObjectsService;

    private final Logger logger = Logger.getLogger("DummyInit");

    public DummyData(TrackableObjectsService trackableObjectsService){
        this.trackableObjectsService = trackableObjectsService;
        try {
            addTestTrackableObject(trackableObjectsService);
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
     * Adding trackable object test data
     * @param trackableObjectRegister Track
     * @throws CouldNotAddTrackableObjectException
     */
    public void addTestTrackableObject(TrackableObjectRegister trackableObjectRegister) throws CouldNotAddTrackableObjectException {
        checkIfObjectIsNull(trackableObjectRegister);
        List<TrackableObject> trackableObjects = new ArrayList<>();
        if (trackableObjectRegister.getAllTrackableObjects().isEmpty()) {
            trackableObjects.add(new TrackableObject("Name Object", TrackableType.WALL, ViewDistance.FAR, 1));
            trackableObjects.add(new TrackableObject("Name Object1", TrackableType.WALL, ViewDistance.FAR, 2));
            trackableObjects.add(new TrackableObject("Name Object2", TrackableType.WALL, ViewDistance.FAR, 3));
            trackableObjects.add(new TrackableObject("Name Object3", TrackableType.WALL, ViewDistance.FAR, 4));
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
