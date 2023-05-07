package no.ntnu.ETIVR.model.trackable;


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
     * Represents the control panel
     */
    CAPTIANOVERLAY,

    /**
     * Represents other observable objects
     */
    OTHER,

    /**
     * Represents undefined objects
     */
    UNDEFINED
}
