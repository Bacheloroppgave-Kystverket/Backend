package no.ntnu.ETIVR.model.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.repository.TrackableObjectsRepository;

public class TrackableObjectsService implements TrackableObjectRegister {
    private final TrackableObjectsRepository trackableObjectsRepository;

    /**
     * Constructor with parameters
     * @param trackableObjectsRepository TrackableObjectRegister
     */
    public TrackableObjectsService(TrackableObjectsRepository trackableObjectsRepository) {
        checkIfObjectIsNull(trackableObjectsRepository, "trackable object repository");
        this.trackableObjectsRepository = trackableObjectsRepository;
    }

    /**
     * Making an iterable to list
     * @param iterable Iterable
     * @return the list
     */
    public List<TrackableObject> iterableToList(Iterable<TrackableObject> iterable) {
        List<TrackableObject> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public void removeTrackableObjectWithID(long trackableObjectID) throws CouldNotRemoveTrackableObjectException {
        checkIfNumberIsAboveZero(trackableObjectID);
        if(trackableObjectsRepository.existsById(trackableObjectID)) {
            trackableObjectsRepository.deleteById(trackableObjectID);
        }
        else {
            throw new CouldNotRemoveTrackableObjectException("The trackable object with ID " + trackableObjectID + " is not in the system");
        }
    }

    /**
     * Add new trackable objects
     * @param trackableObject Trackable objects
     * @return true if added, false if not
     */
    @Override
    public void addTrackableObjects(TrackableObject trackableObject) throws CouldNotAddTrackableObjectException {
        checkIfTrackableObjectIsValid(trackableObject);
        if (!trackableObjectsRepository.existsById(trackableObject.getTrackableObjectID())) {
            trackableObjectsRepository.save(trackableObject);
        } else {
            throw new CouldNotAddTrackableObjectException("The trackable object is already used.");
        }
    }

    /**
     * Delete a trackable object
     * @param trackableObject TrackableObject
     * @return true if deleted, false if not
     */
    @Override
    public void removeTrackableObject(TrackableObject trackableObject) throws CouldNotRemoveTrackableObjectException {
        checkIfTrackableObjectIsValid(trackableObject);
        if(trackableObjectsRepository.existsById(trackableObject.getTrackableObjectID())) {
            trackableObjectsRepository.deleteById(trackableObject.getTrackableObjectID());
        }
        else {
            throw new CouldNotRemoveTrackableObjectException("The trackable object with ID " + trackableObject.getTrackableObjectID() + " is not in the system");
        }
    }

    @Override
    public TrackableObject getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException {
        Optional<TrackableObject> optionalTrackableObject = trackableObjectsRepository.findById(trackableObjectID);
        if (optionalTrackableObject.isEmpty()) {
            throw new CouldNotGetTrackableObjectException("The trackable object with this ID " + trackableObjectID + " is not found in the system");
        }
        return optionalTrackableObject.get();
    }

    /**
     * Get all trackable objects
     * @return list of trackable objects
     */
    @Override
    public List<TrackableObject> getAllTrackableObjects() {
        return iterableToList(trackableObjectsRepository.findAll());
    }


    private void checkIfTrackableObjectIsValid(TrackableObject trackableObject) {
        checkIfObjectIsNull(trackableObject, "trackable object");
    }

    /**
     * Checks if an object is null.
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
     * Checks if the input number is above zero.
     * @param numberToCheck the number to check.
     */
    private void checkIfNumberIsAboveZero(long numberToCheck){
        if (numberToCheck <= 0){
            throw new IllegalArgumentException("The " + "trackable object" + " must be larger than zero.");
        }
    }

}
