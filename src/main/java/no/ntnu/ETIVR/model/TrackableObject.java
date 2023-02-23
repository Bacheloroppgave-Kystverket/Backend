package no.ntnu.ETIVR.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrackableObject {

    ///Id her er bare så jeg får testa
    @Id
    private String nameOfObject;

    /**
     * Constructor with parameters
     * @param nameOfObject String
     */
    public TrackableObject(String nameOfObject) {

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
