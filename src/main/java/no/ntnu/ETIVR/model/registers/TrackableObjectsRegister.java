package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.TrackableObjects;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;

import java.util.List;

public interface TrackableObjectsRegister {
    void addTrackableObjects(TrackableObjects trackableObjects) throws CouldNotAddTrackableObjectException;

    void removeTrackableObjects(TrackableObjects trackableObjects) throws CouldNotRemoveTrackableObjectException;

    void getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException;

    List<TrackableObjects> getAllTrackableObjects();
}
