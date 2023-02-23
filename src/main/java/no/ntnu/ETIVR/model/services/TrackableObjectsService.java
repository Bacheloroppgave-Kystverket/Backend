package no.ntnu.ETIVR.model.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import no.ntnu.ETIVR.model.TrackableObject;
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
    public boolean addNewTrackableObject(TrackableObject trackableObject) {
        boolean added = false;
        if (trackableObject != null) {
            trackableObjectsRepository.save(trackableObject);
            added = true;
        }
        return added;
    }

    /**
     * Delete a trackable object
     * @param trackableObject TrackableObject
     * @return true if deleted, false if not
     */
    public boolean deleteTrackableObject(TrackableObject trackableObject) {
        boolean deleted = false;
        if (trackableObject != null) {
            trackableObjectsRepository.delete(trackableObject);
            deleted = true;
        }
        return deleted;
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
}
