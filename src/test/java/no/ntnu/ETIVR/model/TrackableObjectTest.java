package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.trackable.ViewDistance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the trackable object class.
 */
class TrackableObjectTest extends DefaultTest {

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
    }

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    /**
     * Tests constructor with invalid input
     */
    @Test
    @DisplayName("Tests constructor with invalid input")
    void testConstructorInvalidInput() {
        String nameOfObject = "Window";
        TrackableType trackableType = TrackableType.WALL;
        ViewDistance viewDistance = ViewDistance.FAR;
        long trackableObjectId = 2L;

        TrackableObject trackableObject;

        try {
            trackableObject = new TrackableObject("", trackableType, trackableObjectId);
            addError(getIllegalPrefix(), "the name of the object cannot be empty");
        } catch (IllegalArgumentException e) {
        }
        try {
            trackableObject = new TrackableObject(nameOfObject, null, trackableObjectId);
            addError(getIllegalPrefix(), "the trackable type of the object cannot be null");
        } catch (IllegalArgumentException e) {
        }
        try {
            trackableObject = new TrackableObject(nameOfObject, trackableType, -1L);
            addError(getIllegalPrefix(), "the trackable object id of the object cannot be 0");
        } catch (IllegalArgumentException e) {
        }
    }


    /**
     * Tests constructor with valid input
     */
    @Test
    @DisplayName("Tests constructor with valid input")
    void testConstructorValidInput() {
        String nameOfObject = "Window";
        TrackableType trackableType = TrackableType.WALL;
        ViewDistance viewDistance = ViewDistance.FAR;
        long trackableObjectId = 2L;
        try {
            TrackableObject trackableObject = new TrackableObject(nameOfObject, trackableType, trackableObjectId);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Expected the trackable object", "to be made", e);
        }
    }

}
