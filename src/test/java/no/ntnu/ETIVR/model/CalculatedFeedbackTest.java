package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CalculatedFeedbackTest extends DefaultTest{

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
     * Tests if the constructor works with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input")
    public void testIfConstructorWorksWithValidInput(){
        TrackableType trackableType = TrackableType.CUBE;
        float prosentage = 1f;
        CalculatedFeedback calculatedFeedback;
        try{
            calculatedFeedback = new CalculatedFeedback(trackableType, prosentage);
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected the calculated feedback to be made", "since the input is valid", exception);
        }
    }


    /**
     * Tests if the constructor works with invalid input.
     */
    @DisplayName("Tests if the constructor works with invalid input")
    @Test
    public void testIfConstructorWorksWithInvalidInput(){
        TrackableType trackableType = TrackableType.CUBE;
        float prosentage = 1f;
        CalculatedFeedback calculatedFeedback;
        try {
            calculatedFeedback = new CalculatedFeedback(null, prosentage);
            addError(getIllegalPrefix(), "the trackable type is null");
        }catch (IllegalArgumentException exception){}
        try{
            calculatedFeedback = new CalculatedFeedback(trackableType, -2f);
            addError(getIllegalPrefix(), "the input prosentage is negative");
        }catch (IllegalArgumentException exception){}
    }

}
