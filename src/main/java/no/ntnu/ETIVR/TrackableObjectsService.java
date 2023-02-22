package no.ntnu.ETIVR;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<TrackableObjects> iterableToList(Iterable<TrackableObjects> iterable) {
        List<TrackableObjects> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    /**
     * Find a trackable object by name
     * @param name String
     * @return The trackable object or null if not found
     */
    public TrackableObjects findTrackableObjectByName(String name) {
        Optional<TrackableObjects> trackableObjects = trackableObjectsRepository.findById(name);
        return trackableObjects.orElse(null);
    }

    /**
     * Get all trackable objects
     * @return list of trackable objects
     */
    public List<TrackableObjects> getAll() {
        return iterableToList(trackableObjectsRepository.findAll());
    }

    /**
     * Add new trackable objects
     * @param trackableObjects Trackable objects
     * @return true if added, false if not
     */
    public boolean addNewTrackableObject(TrackableObjects trackableObjects) {
        boolean added = false;
        if (trackableObjects != null) {
            trackableObjectsRepository.save(trackableObjects);
            added = true;
        }
        return added;
    }

    /**
     * Delete a trackable object
     * @param trackableObjects TrackableObject
     * @return true if deleted, false if not
     */
    public boolean deleteTrackableObject(TrackableObjects trackableObjects) {
        boolean deleted = false;
        if (trackableObjects != null) {
            trackableObjectsRepository.delete(trackableObjects);
            deleted = true;
        }
        return deleted;
    }


    /**
     * Update trackable object by name
     * @param name String
     * @param trackableObjects TrackableObject
     * @return error message telling whether it has been updated or not
     */
    public String updateTrackableObject(String name, TrackableObjects trackableObjects) {
        TrackableObjects existingTrackableObject = findTrackableObjectByName(name);
        String errorMessage = null;
        if (existingTrackableObject == null) {
            errorMessage = "No trackable object with " + name + "found";
        }
        if (trackableObjects == null) {
            errorMessage = "Please check if your data is correct";
        }
        else if (!Objects.equals(trackableObjects.getNameOfObject(), name)) {
            errorMessage = "Please check your name, it does not match";
        }
        if (errorMessage == null) {
            trackableObjectsRepository.save(trackableObjects);
        }
        return errorMessage;
    }
}
