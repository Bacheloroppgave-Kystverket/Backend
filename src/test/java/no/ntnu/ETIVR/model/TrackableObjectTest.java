package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TrackableObjectTest extends DefaultTest{

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

    @Test
    @DisplayName("Tests constructor with invalid input")
    void testConstructorInvalidInput() {
        String nameOfObject = "Window";
        TrackableType trackableType = TrackableType.WALL;
        ViewDistance viewDistance = ViewDistance.FAR;
        long trackableObjectId = 2L;

        TrackableObject trackableObject;

        try {
            trackableObject = new TrackableObject("", trackableType, viewDistance, trackableObjectId);
            addError(getIllegalPrefix(), "the name of the object cannot be empty");
        } catch (IllegalArgumentException e) {}
        try {
            trackableObject = new TrackableObject(nameOfObject, null, viewDistance, trackableObjectId);
            addError(getIllegalPrefix(), "the trackable type of the object cannot be null");
        } catch (IllegalArgumentException e) {}
        try {
            trackableObject = new TrackableObject(nameOfObject, trackableType, null, trackableObjectId);
            addError(getIllegalPrefix(), "the view distance of the object cannot be null");
        } catch (IllegalArgumentException e) {}
        try {
            trackableObject = new TrackableObject(nameOfObject, trackableType, viewDistance, -1L);
            addError(getIllegalPrefix(), "the trackable object id of the object cannot be 0");
        } catch (IllegalArgumentException e) {}
    }


    @Test
    @DisplayName("Tests constructor with valid input")
    void testConstructorValidInput() {
        String nameOfObject = "Window";
        TrackableType trackableType = TrackableType.WALL;
        ViewDistance viewDistance = ViewDistance.FAR;
        long trackableObjectId = 2L;
        try {
            TrackableObject trackableObject = new TrackableObject(nameOfObject, trackableType, viewDistance, trackableObjectId);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Expected the trackable object", "to be made", e);
        }
    }

}
