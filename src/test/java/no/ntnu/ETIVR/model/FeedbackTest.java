package no.ntnu.ETIVR.model;

import java.util.HashMap;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class FeedbackTest extends DefaultTest {

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
  @DisplayName("Tests if the constructor works with invalid input")
  public void testConstructorWorksWithInValidInput(){
    try {
      Feedback feedback =  new Feedback(null);
      addError(getIllegalPrefix(), "the input hashmap is null");
    }catch (IllegalArgumentException exception){}
  }

  /**
   * Tests if the constructor works with valid input.
   */
  @Test
  @DisplayName("Tests if the constructor works with valid input")
  public void testConstructorWorksWithValidInput(){
    HashMap<TrackableType, Float> trackableTypeFloatHashMap = new HashMap<>();
    trackableTypeFloatHashMap.put(TrackableType.CUBE, 0.3f);
    try {
      Feedback feedback = new Feedback(null);
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected to get a feedback object since", "the input is valid", exception);
    }
  }
}
