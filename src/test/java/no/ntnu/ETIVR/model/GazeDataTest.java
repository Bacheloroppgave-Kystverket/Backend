package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.GazeData;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the gaze data class.
 */
public class GazeDataTest extends DefaultTest {

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
     * Makes reference position.
     * @return the reference position.
     */
    private ReferencePosition makeReferencePosition() {
        return new ReferencePosition(1, "hei", makePositionConfiguration());
    }

    /**
     * Makes a new default position configuration.
     * @return the position configuration.
     */
    private PositionConfiguration makePositionConfiguration() {
        List<CategoryConfiguration> categoryFeedbacks = new ArrayList<>();
        categoryFeedbacks.add(new CategoryConfiguration(TrackableType.OTHER, 0.5f));
        return new PositionConfiguration(categoryFeedbacks);
    }

    /**
     * Tests if the constructor works with invalid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with invalid input.")
    public void testIfConstructorWorksWithInvalidInput() {
        int fixations = 10;
        float fixationDuration = 10f;
        ReferencePosition referencePosition = makeReferencePosition();
        GazeData gazeData;
        try {
            gazeData = new GazeData(-2, fixationDuration, referencePosition);
            addError(getIllegalPrefix(), "the input fixations is negative");
        } catch (IllegalArgumentException exception) {
        }
        try {
            gazeData = new GazeData(fixations, -2, referencePosition);
            addError(getIllegalPrefix(), "the fixation duration is negative");
        } catch (IllegalArgumentException exception) {
        }
        try {
            gazeData = new GazeData(fixations, fixationDuration, null);
            addError(getIllegalPrefix(), "the reference position is null");
        } catch (IllegalArgumentException exception) {
        }
    }

    /**
     * Tests if the constructor works with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input.")
    public void testIfConstructorWorksWithValidInput() {
        int fixations = 10;
        float fixationDuration = 10f;
        ReferencePosition referencePosition = makeReferencePosition();
        GazeData gazeData;
        try {
            gazeData = new GazeData(fixations, fixationDuration, referencePosition);
        } catch (IllegalArgumentException exception) {
            addErrorWithException("Expected the gaze data to be made since", "the input is valid", exception);
        }
    }
}
