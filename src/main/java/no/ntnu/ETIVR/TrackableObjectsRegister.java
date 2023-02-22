package no.ntnu.ETIVR;

import no.ntnu.ETIVR.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.exceptions.CouldNotRemoveTrackableObjectException;

import java.util.List;

public interface TrackableObjectsRegister {
    void addTrackableObjects(TrackableObjects trackableObjects) throws CouldNotAddTrackableObjectException;

    void removeTrackableObjects(TrackableObjects trackableObjects) throws CouldNotRemoveTrackableObjectException;

    void getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException;

    List<TrackableObjects> getAllTrackableObjects();
}
