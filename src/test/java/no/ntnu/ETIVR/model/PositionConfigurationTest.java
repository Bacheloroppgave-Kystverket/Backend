package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PositionConfigurationTest extends DefaultTest {

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
     * Tests if the constructor works with invalid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with invalid input.")
    public void testIfConstructorWorksWithInvalidInput(){
        try {
            PositionConfiguration positionConfiguration = new PositionConfiguration(null);
            addError(getIllegalPrefix(),"the input categories is null");
        }catch (IllegalArgumentException exception){}
    }

    /**
     * Tests if the constructor works with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input.")
    public void testIfConstructorWorksWithValidInput(){
        try {
            PositionConfiguration positionConfiguration = new PositionConfiguration(new ArrayList<>());
        }catch (IllegalArgumentException exception){
            addErrorWithException("Expected the position configuration to be made", "since the input is valid", exception);
        }
    }
}
