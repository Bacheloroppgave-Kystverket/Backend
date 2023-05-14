package no.ntnu.ETIVR.model.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.repository.TrackableObjectRepository;
import org.springframework.stereotype.Service;

/**
 * Represents the trackable objects service that handles JPA interactions.
 */
@Service
public class TrackableObjectsService implements TrackableObjectRegister {

    private final TrackableObjectRepository trackableObjectRepository;

    /**
     * Constructor with parameters
     * @param trackableObjectRepository TrackableObjectRegister
     */
    public TrackableObjectsService(TrackableObjectRepository trackableObjectRepository) {
        checkIfObjectIsNull(trackableObjectRepository, "trackable object repository");
        this.trackableObjectRepository = trackableObjectRepository;
    }

    @Override
    public void addTrackableObject(TrackableObject trackableObject) throws CouldNotAddTrackableObjectException {
        checkIfTrackableObjectIsValid(trackableObject);
        if (!trackableObjectRepository.existsById(trackableObject.getTrackableObjectID())) {
            trackableObjectRepository.save(trackableObject);
        } else {
            throw new CouldNotAddTrackableObjectException("The trackable object is already used.");
        }
    }

    @Override
    public void removeTrackableObject(TrackableObject trackableObject) throws CouldNotRemoveTrackableObjectException {
        checkIfTrackableObjectIsValid(trackableObject);
        if (trackableObjectRepository.existsById(trackableObject.getTrackableObjectID())) {
            trackableObjectRepository.deleteById(trackableObject.getTrackableObjectID());
        } else {
            throw new CouldNotRemoveTrackableObjectException("The trackable object with ID " + trackableObject.getTrackableObjectID() + " is not in the system");
        }
    }

    @Override
    public void removeTrackableObjectWithID(long trackableObjectID) throws CouldNotRemoveTrackableObjectException {
        checkIfNumberIsAboveZero(trackableObjectID);
        if (trackableObjectRepository.existsById(trackableObjectID)) {
            trackableObjectRepository.deleteById(trackableObjectID);
        } else {
            throw new CouldNotRemoveTrackableObjectException("The trackable object with ID " + trackableObjectID + " is not in the system");
        }
    }


    @Override
    public TrackableObject getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException {
        checkIfNumberIsAboveZero(trackableObjectID);
        Optional<TrackableObject> optionalTrackableObject = trackableObjectRepository.findById(trackableObjectID);
        if (optionalTrackableObject.isEmpty()) {
            throw new CouldNotGetTrackableObjectException("The trackable object with this ID " + trackableObjectID + " is not found in the system");
        }
        return optionalTrackableObject.get();
    }

    @Override
    public List<TrackableObject> getAllTrackableObjects() {
        return iterableToList(trackableObjectRepository.findAll());
    }

    /**
     * Making an iterable to list
     * @param iterable Iterable
     * @return the list
     */
    private List<TrackableObject> iterableToList(Iterable<TrackableObject> iterable) {
        List<TrackableObject> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Check if trackable object is valid
     * @param trackableObject Trackable
     */
    private void checkIfTrackableObjectIsValid(TrackableObject trackableObject) {
        checkIfObjectIsNull(trackableObject, "trackable object");
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }

    /**
     * Checks if the input number is above zero.
     * @param numberToCheck the number to check.
     */
    private void checkIfNumberIsAboveZero(long numberToCheck) {
        if (numberToCheck <= 0) {
            throw new IllegalArgumentException("The " + "trackable object" + " must be larger than zero.");
        }
    }

}
