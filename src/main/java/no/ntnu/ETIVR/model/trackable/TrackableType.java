package no.ntnu.ETIVR.model.trackable;

/**
 * Represents a trackable type for a object. Is a general category that makes finding key categories and their prosentage
 * easier.
 */
public enum TrackableType {

    /**
     * Represents objects that are walls
     */
    WALL,

    /**
     * Represents objects that are windows
     */
    WINDOW,

    /**
     * Represents objects that are mirrors
     */
    MIRROR,

    /**
     * Represents the radar.
     */
    RADAR,

    /**
     * Represents objects that can be picked up.
     */
    PICKUPABLES,

    /**
     * Represents the menu option
     */
    MENU,

    /**
     * Represents the officer overlay
     */
    OFFICEROVERLAY,

    /**
     * Represents the captain overlay
     */
    CAPTAINOVERLAY,

    /**
     * Represents other observable objects
     */
    OTHER,

    /**
     * Represents undefined objects
     */
    UNDEFINED
}
