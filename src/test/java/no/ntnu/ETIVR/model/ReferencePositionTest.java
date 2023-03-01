package no.ntnu.ETIVR.model;

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
    ReferencePosition referencePosition;
    try {
      referencePosition = new ReferencePosition(0, locationName, positionDuration);
      addError(getIllegalPrefix(), "the input location id is 0");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, null, positionDuration);
      addError(getIllegalPrefix(), "the input location name is null");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, "", positionDuration);
      addError(getIllegalPrefix(), "the input location name is empty");
    }catch (IllegalArgumentException exception){}
    try {
      referencePosition = new ReferencePosition(locationId, locationName, -1);
      addError(getIllegalPrefix(), "the input position duration is negative");
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
    Float positionDuration = 10f;
    ReferencePosition referencePosition;
    try {
      referencePosition = new ReferencePosition(locationId, locationName, positionDuration);
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected the reference position", "to be made", exception);
    }
  }


}
