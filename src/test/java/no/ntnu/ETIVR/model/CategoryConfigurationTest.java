package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CategoryConfigurationTest extends  DefaultTest {

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
    @DisplayName("Tests if the constructor works with valid input")
    @Test
    public void testIfConstructorWorksWithValidInput(){
        TrackableType trackableType = TrackableType.WALL;
        float threshold = 1f;
        try{
            CategoryConfiguration
                categoryConfiguration = new CategoryConfiguration(trackableType, threshold);
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected the feedback configuration to be made", "since the input is valid", exception);
        }
    }


    /**
     * Tests if the constructor works with invalid input.
     */
    @DisplayName("Tests if the constructor works with invalid input.")
    @Test
    public void testIfConstructorWorksWithInvalidInput(){
        TrackableType trackableType = TrackableType.WALL;
        float threshold = 1f;
        try{
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration(null, threshold);
            addError(getIllegalPrefix(),"the input trackable type is null");
        }catch (IllegalArgumentException exception){}
        try{
            CategoryConfiguration categoryConfiguration = new CategoryConfiguration(trackableType, -2f);
            addError(getIllegalPrefix(), "the input threshold is negative");
        }catch (IllegalArgumentException exception){}
        try {
            CategoryConfiguration
                categoryConfiguration = new CategoryConfiguration(trackableType, 1.2f);
            addError(getIllegalPrefix(), "the input threshold is above 1");
        }catch (IllegalArgumentException exception){}
    }


}
