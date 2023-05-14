package no.ntnu.ETIVR.model.trackable;

/**
 * Represents if the object is close to the player like part of the boat, or if its in the distance and not part of the
 * predefined setup.
 */
public enum ViewDistance {
    /**
     * Represents an object that is close and a part of the main section of the simulation
     */
    CLOSE,

    /**
     * Represents an object that is a further way from the main section
     */
    FAR
}
