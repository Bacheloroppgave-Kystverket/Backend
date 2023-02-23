package no.ntnu.ETIVR.model;


public class TrackableObjects {
    private String nameOfObject;

    /**
     * Constructor with parameters
     * @param nameOfObject String
     */
    public TrackableObjects(String nameOfObject) {

        this.nameOfObject = nameOfObject;
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
}
