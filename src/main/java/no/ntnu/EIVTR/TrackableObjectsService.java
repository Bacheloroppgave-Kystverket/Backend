package no.ntnu.EIVTR;

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TrackableObjectsService {
    private TrackableObjectsRegister trackableObjectsRegister;

    public TrackableObjectsService(TrackableObjectsRegister trackableObjectsRegister) {
        this.trackableObjectsRegister = trackableObjectsRegister;
    }

    public List<TrackableObjects> iterableToList(Iterable<TrackableObjects> iterable) {
        List<TrackableObjects> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public TrackableObjects findTrackableObjectByName(String name) {
        Optional<TrackableObjects> trackableObjects = trackableObjectsRegister.findById(name);
        return trackableObjects.orElse(null);
    }

    public List<TrackableObjects> getAll() {
        return iterableToList(trackableObjectsRegister.findAll());
    }

    public boolean addNewTrackableObject(TrackableObjects trackableObjects) {
        boolean added = false;
        if (trackableObjects != null) {
            trackableObjectsRegister.save(trackableObjects);
            added = true;
        }
        return added;
    }

    public boolean deleteTrackableObject(TrackableObjects trackableObjects) {
        boolean deleted = false;
        if (trackableObjects != null) {
            trackableObjectsRegister.delete(trackableObjects);
            deleted = true;
        }
        return deleted;
    }

    public String updateTrackableObject(String name, TrackableObjects trackableObjects) {
        TrackableObjects existingTrackableObject = findTrackableObjectByName(name);
        String errorMessage = null;
        if (existingTrackableObject == null) {
            errorMessage = "No trackable object with " + name + "found";
        }
        if (trackableObjects == null) {
            errorMessage = "Please check if your data is correct";
        }
        else if (trackableObjects.getNameOfObject() != name) {
            errorMessage = "Please check your name, it does not match";
        }
        if (errorMessage == null) {
            trackableObjectsRegister.save(trackableObjects);
        }
        return errorMessage;
    }
}
