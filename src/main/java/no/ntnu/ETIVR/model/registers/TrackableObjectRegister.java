package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.TrackableObject;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;

import java.util.List;

public interface TrackableObjectRegister {
    void addTrackableObjects(TrackableObject trackableObject) throws CouldNotAddTrackableObjectException;

    void removeTrackableObject(TrackableObject trackableObject) throws CouldNotRemoveTrackableObjectException;

    TrackableObject getTrackableObjectById(long trackableObjectID) throws CouldNotGetTrackableObjectException;

    List<TrackableObject> getAllTrackableObjects();
}
