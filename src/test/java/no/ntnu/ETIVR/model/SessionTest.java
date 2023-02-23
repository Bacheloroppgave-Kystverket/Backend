package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SessionTest {
    private Session session;

    private StringBuilder stringBuilder;

    private int errors;

    private final String illegalPrefix;

    private ArrayList<TrackableObject> list;

    public SessionTest() {
        this.illegalPrefix = makeExceptionString();
    }

    /**
     * Makes an exception into the wanted string.
     * @return the full exception string.
     */
    private String makeExceptionString(){
        return "Expected to get a " + "IllegalArgumentException" + " since";
    }

    private void addError(String errorPrefix, String error) {
        stringBuilder.append("\n").append(errorPrefix).append(error);
    }

    public void SetUpTestSession() {
        try {
            session = new Session(list, 12.123f, 1L);
        } catch (IllegalArgumentException e) {
            fail("The input is invalid. Session is expected to be made.");
        }
        stringBuilder = new StringBuilder();
        errors = 0;
    }

    /**
     * Checks if the tests failed and displays the results.
     */
    private void checkIfTestsFailedAndDisplayResult(){
        if(stringBuilder.length() == 0){
            assertTrue(true);
        }else {
            fail("\nAmount of errors " + errors + " listed errors: " + stringBuilder.toString());
        }
    }

    @Test
    @DisplayName("Tests if getTrackableObject works with invalid input.")
    public void testGetTrackableObjectsInvalidInput() {
        try {
            session.getTrackableObjects();
            fail("Expected to get an IllegalArgumentException sine the input is invalid.");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }







}
