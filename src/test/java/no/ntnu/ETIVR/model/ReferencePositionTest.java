package no.ntnu.ETIVR.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class ReferencePositionTest extends DefaultTest {

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
  @DisplayName("Tests if constructor works with invalid input")
  public void testConstructorWorksWithInvalidInput(){
    long locationId = 1;
    String locationName = "Position 1";
    float positionDuration = 10f;
    List<FeedbackConfiguration> feedbackConfigurationList = new ArrayList<>();
    feedbackConfigurationList.add(new FeedbackConfiguration(TrackableType.WALL, 0.1f));
    ReferencePosition referencePosition;
    try {
      referencePosition = new ReferencePosition(-1, locationName, positionDuration, feedbackConfigurationList);
      addError(getIllegalPrefix(), "the input location id is 0");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, null, positionDuration, feedbackConfigurationList);
      addError(getIllegalPrefix(), "the input location name is null");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, "", positionDuration, feedbackConfigurationList);
      addError(getIllegalPrefix(), "the input location name is empty");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, locationName, -1, feedbackConfigurationList);
      addError(getIllegalPrefix(), "the input position duration is negative");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, locationName, positionDuration, null);
      addError(getIllegalPrefix(), "the input configuration is null");
    }catch (IllegalArgumentException exception){

    }
  }

  /**
   * Tests if the constructor works with valid input.
   */
  @Test
  @DisplayName("Tests if constructor works with valid input")
  public void testConstructorWithValidInput(){
    long locationId = 1;
    String locationName = "Position 1";
    Float positionDuration = 10f;
    List<FeedbackConfiguration> feedbackConfigurationList = new ArrayList<>();
    feedbackConfigurationList.add(new FeedbackConfiguration(TrackableType.WALL, 0.1f));
    ReferencePosition referencePosition;
    try {
      referencePosition = new ReferencePosition(locationId, locationName, positionDuration, feedbackConfigurationList);
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected the reference position", "to be made", exception);
    }
  }


}
