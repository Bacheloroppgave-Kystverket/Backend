package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;

import java.util.List;

/**
 * Represents a trackable objects register.
 */
public interface TrackableObjectRegister {

    /**
     * Adds a trackable object to the register.
     * @param trackableObject the trackable object
     * @throws CouldNotAddTrackableObjectException gets thrown if the trackable object could not be added.
     */
    void addTrackableObject(TrackableObject trackableObject) throws CouldNotAddTrackableObjectException;

    /**
     * Removes a trackable object from the register.
     * @param trackableObject the trackable object.
     * @throws CouldNotRemoveTrackableObjectException gets thrown if the trackable object could not be removed.
     */
    void removeTrackableObject(TrackableObject trackableObject) throws CouldNotRemoveTrackableObjectException;

    /**
     * Remove trackable object with ID
     * @param trackableObjectID long
     * @throws CouldNotRemoveTrackableObjectException gets thrown if it is not in the system
     */
    void removeTrackableObjectWithID(long trackableObjectID) throws CouldNotRemoveTrackableObjectException;
    /**
     * Get trackable object by ID
     * @param trackableObjectID long
     * @return Trackable object if it exists
     * @throws CouldNotGetTrackableObjectException if it is not found in the system
     */
    TrackableObject getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException;

    /**
     * Gets all the trackable objects.
     * @return all the trackable objects.
     */
    List<TrackableObject> getAllTrackableObjects();
}
