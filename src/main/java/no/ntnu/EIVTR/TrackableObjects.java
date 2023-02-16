package no.ntnu.EIVTR;

import java.util.Collection;

public class TrackableObjects {
    private String nameOfObject;
    private Collection<Integer> GazeData;

    /**
     * Constructor with parameters
     * @param nameOfObject String
     * @param gazeData Collection of integers
     */
    public TrackableObjects(String nameOfObject, Collection<Integer> gazeData) {
        this.nameOfObject = nameOfObject;
        GazeData = gazeData;
    }

    /**
     * Get name of object
     * @return name of object
     */
    public String getNameOfObject() {
        return nameOfObject;
    }

    /**
     * Set name of object
     * @param nameOfObject String
     */
    public void setNameOfObject(String nameOfObject) {
        this.nameOfObject = nameOfObject;
    }

    /**
     * Get collection of integers
     * @return gaze data
     */
    public Collection<Integer> getGazeData() {
        return GazeData;
    }

    /**
     * Set collection of gaze data
     * @param gazeData Collection of integers
     */
    public void setGazeData(Collection<Integer> gazeData) {
        GazeData = gazeData;
    }
}
