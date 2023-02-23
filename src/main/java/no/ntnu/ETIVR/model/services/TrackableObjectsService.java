package no.ntnu.ETIVR.model.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;
import no.ntnu.ETIVR.model.repository.TrackableObjectsRepository;

public class TrackableObjectsService {
    private final TrackableObjectsRepository trackableObjectsRepository;

    /**
     * Constructor with parameters
     * @param trackableObjectsRepository TrackableObjectRegister
     */
    public TrackableObjectsService(TrackableObjectsRepository trackableObjectsRepository) {
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

    /**
     * Find a trackable object by name
     * @param name String
     * @return The trackable object or null if not found
     */
    public TrackableObject findTrackableObjectByName(String name) {
        Optional<TrackableObject> trackableObjects = trackableObjectsRepository.findById(name);
        return trackableObjects.orElse(null);
    }

    /**
     * Get all trackable objects
     * @return list of trackable objects
     */
    public List<TrackableObject> getAll() {
        return iterableToList(trackableObjectsRepository.findAll());
    }

    /**
     * Add new trackable objects
     * @param trackableObject Trackable objects
     * @return true if added, false if not
     */
    public void addNewTrackableObject(TrackableObject trackableObject) throws CouldNotAddTrackableObjectException{
        checkIfTrackableObjectIsValid(trackableObject);
        Optional<TrackableObject> optionalTrackableObject = trackableObjectsRepository.existsById(trackableObject.getTrackableType());
        if (trackableObject != null) {
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
    public void deleteTrackableObject(TrackableObject trackableObject) throws CouldNotRemoveTrackableObjectException {
        checkIfTrackableObjectIsValid(trackableObject);
    }

    public void removeTrackableObjectWithID(long trackableObjectID) throws CouldNotRemoveTrackableObjectException {
        checkIfNumberIsAboveZero(trackableObjectID, "trackable object");
        if(trackableObjectsRepository.existsById(trackableObjectID)) {
            trackableObjectsRepository.deleteById(trackableObjectID);
        }
        else {
            throw new CouldNotRemoveTrackableObjectException("The trackable object with ID " + trackableObjectID + " is not in the system");
        }
    }


    /**
     * Update trackable object by name
     * @param name String
     * @param trackableObject TrackableObject
     * @return error message telling whether it has been updated or not
     */
    public String updateTrackableObject(String name, TrackableObject trackableObject) {
        TrackableObject existingTrackableObject = findTrackableObjectByName(name);
        String errorMessage = null;
        if (existingTrackableObject == null) {
            errorMessage = "No trackable object with " + name + "found";
        }
        if (trackableObject == null) {
            errorMessage = "Please check if your data is correct";
        }
        else if (!Objects.equals(trackableObject.getNameOfObject(), name)) {
            errorMessage = "Please check your name, it does not match";
        }
        if (errorMessage == null) {
            trackableObjectsRepository.save(trackableObject);
        }
        return errorMessage;
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
    private void checkIfNumberIsAboveZero(long numberToCheck, String prefix){
        if (numberToCheck <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be larger than zero.");
        }
    }
}
