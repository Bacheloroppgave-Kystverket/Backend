package no.ntnu.ETIVR.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.trackable.TrackableType;
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
   * Makes a new default position configuration.
   * @return the position configuration.
   */
  private PositionConfiguration makePositionConfiguration(){
    List<CategoryConfiguration> categoryFeedbacks = new ArrayList<>();
    categoryFeedbacks.add(new CategoryConfiguration(TrackableType.OTHER, 0.5f));
    return new PositionConfiguration(categoryFeedbacks);
  }

  /**
   * Tests if the constructor works with invalid input.
   */
  @Test
  @DisplayName("Tests if constructor works with invalid input")
  public void testConstructorWorksWithInvalidInput(){
    long locationId = 1;
    String locationName = "Position 1";
    List<CategoryConfiguration> categoryConfigurationList = new ArrayList<>();
    categoryConfigurationList.add(new CategoryConfiguration(TrackableType.WALL, 0.1f));
    ReferencePosition referencePosition;
    PositionConfiguration positionConfiguration = makePositionConfiguration();
    try {
      referencePosition = new ReferencePosition(-1, locationName, positionConfiguration);
      addError(getIllegalPrefix(), "the input location id is 0");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, null, positionConfiguration);
      addError(getIllegalPrefix(), "the input location name is null");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, "", positionConfiguration);
      addError(getIllegalPrefix(), "the input location name is empty");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, locationName, null);
      addError(getIllegalPrefix(), "the input position configuration is null");
    }catch (IllegalArgumentException exception){}
  }

  /**
   * Tests if the constructor works with valid input.
   */
  @Test
  @DisplayName("Tests if constructor works with valid input")
  public void testConstructorWithValidInput(){
    long locationId = 1;
    String locationName = "Position 1";
    List<CategoryConfiguration> categoryConfigurationList = new ArrayList<>();
    categoryConfigurationList.add(new CategoryConfiguration(TrackableType.WALL, 0.1f));
    ReferencePosition referencePosition;
    PositionConfiguration positionConfiguration = makePositionConfiguration();
    try {
      referencePosition = new ReferencePosition(locationId, locationName, positionConfiguration);
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected the reference position", "to be made", exception);
    }
  }


}
