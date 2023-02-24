package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class SessionTest extends DefaultTest{

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
    @DisplayName("Tests if the constructor works with invalid input")
    void testConstructorInvalidInput() {
        List<TrackableObject> trackableObjects = new ArrayList<>();
        List<ReferencePosition> referencePositions = new ArrayList<>();
        List<Feedback> feedbackLog = new ArrayList<>();
        try{
            Session session = new Session(LocalDateTime.now(), 2, trackableObjects, 3, referencePositions, feedbackLog);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Expected the session", "to made", e);
        }
    }



    @Test
    @DisplayName("Tests if the constructor works with valid input")
    void testConstructorValidInput() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<TrackableObject> trackableObjects = new ArrayList<>();
        long sessionId = 2L;
        int userId = 1;
        List<ReferencePosition> referencePositions = new ArrayList<>();
        List<Feedback> feedbackLog = new ArrayList<>();

        Session session;

        try{
            session = new Session(currentDate, -1, trackableObjects, sessionId, referencePositions, feedbackLog);
            addError(getIllegalPrefix(), "the user id cannot be under 0");
        } catch (IllegalArgumentException e) {}
        try {
            session = new Session(currentDate, userId, null, sessionId, referencePositions, feedbackLog);
            addError(getIllegalPrefix(), "the trackable objects cannot be null");
        } catch (IllegalArgumentException e) {}
        try {
            session = new Session(currentDate, userId, trackableObjects, 0L, referencePositions, feedbackLog);
            addError(getIllegalPrefix(), "the session id cannot be 0");
        } catch (IllegalArgumentException e) {}
        try {
            session = new Session(currentDate, userId, trackableObjects, sessionId, null, feedbackLog);
            addError(getIllegalPrefix(), "the reference position cannot be null");
        } catch (IllegalArgumentException e) {}
        try {
            session = new Session(currentDate, userId, trackableObjects, sessionId, referencePositions, null);
            addError(getIllegalPrefix(), "the feedback log cannot be null");
        } catch (IllegalArgumentException e) {}
    }
}
