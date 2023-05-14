package no.ntnu.ETIVR.model;

import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the trackable record class.
 */
public class TrackableRecordTest extends DefaultTest {


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
     * Makes a default reference position.
     * @return the reference position.
     */
    private ReferencePosition makeReferencePosition() {
        return new ReferencePosition(50000, "Seat 1", makePositionConfiguration());
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
     * Makes default gaze data.
     * @param referencePosition the reference position
     * @return the gaze data.
     */
    private List<GazeData> makeGazeData(ReferencePosition referencePosition) {
        List<GazeData> gazeData = new ArrayList<>();
        gazeData.add(new GazeData(1, 1, referencePosition));
        return gazeData;
    }

    /**
     * Makes a default trackable object
     * @return the trackable object
     */
    private TrackableObject makeTrackableObject() {
        return new TrackableObject("Pog", TrackableType.WALL, 5000);
    }

    /**
     * Tests if the constructor works with invalid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with invalid input.")
    public void testIfConstructorWorksWithInvalidInput() {
        ReferencePosition referencePosition = makeReferencePosition();
        List<GazeData> gazeData = makeGazeData(referencePosition);
        TrackableObject trackableObject = makeTrackableObject();
        ViewDistance viewDistance = ViewDistance.CLOSE;

        TrackableRecord trackableRecord;
        try {
            trackableRecord = new TrackableRecord(null, viewDistance, trackableObject);
            addError(getIllegalPrefix(), "the input gaze list is null");
        } catch (IllegalArgumentException exception) {
        }
        try {
            trackableRecord = new TrackableRecord(gazeData, null, trackableObject);
            addError(getIllegalPrefix(), "the input view distance is null");
        } catch (IllegalArgumentException exception) {
        }
        try {
            trackableRecord = new TrackableRecord(gazeData, viewDistance, null);
            addError(getIllegalPrefix(), "the input trackable object is null");
        } catch (IllegalArgumentException exception) {
        }
    }

    /**
     * Tests if the constructor works with valid input.
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input.")
    public void testIfConstructorWorksWithValidInput() {
        ReferencePosition referencePosition = makeReferencePosition();
        List<GazeData> gazeData = makeGazeData(referencePosition);
        TrackableObject trackableObject = makeTrackableObject();
        ViewDistance viewDistance = ViewDistance.CLOSE;

        TrackableRecord trackableRecord;
        try {
            trackableRecord = new TrackableRecord(gazeData, viewDistance, trackableObject);
        } catch (IllegalArgumentException exception) {
            addErrorWithException("Expected the trackable record to be made since", "the input is valid", exception);
        }
    }

}
