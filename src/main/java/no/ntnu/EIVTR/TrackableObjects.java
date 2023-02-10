package no.ntnu.EIVTR;

import java.util.Collection;

public class TrackableObjects {
    private String nameOfObject;
    private Collection<Integer> GazeData;

    public TrackableObjects(String nameOfObject, Collection<Integer> gazeData) {
        this.nameOfObject = nameOfObject;
        GazeData = gazeData;
    }

    public String getNameOfObject() {
        return nameOfObject;
    }

    public void setNameOfObject(String nameOfObject) {
        this.nameOfObject = nameOfObject;
    }

    public Collection<Integer> getGazeData() {
        return GazeData;
    }

    public void setGazeData(Collection<Integer> gazeData) {
        GazeData = gazeData;
    }
}
