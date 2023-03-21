package no.ntnu.ETIVR.model;

import java.util.ArrayList;

import java.util.List;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class AdaptiveFeedbackTest extends DefaultTest {

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
    String positionName = "Seat 1";
    float positionTime = 1f;
    List<CategoryFeedback> feedbackList = new ArrayList<>();
    AdaptiveFeedback adaptiveFeedback;
    ReferencePosition referencePosition = new ReferencePosition(0, "Seat 1");
    try {
      adaptiveFeedback = new AdaptiveFeedback(-2, feedbackList, referencePosition.getLocationName());
      addError(getIllegalPrefix(), "the position time is negative");
    }catch (IllegalArgumentException exception){}
    try {
      adaptiveFeedback = new AdaptiveFeedback(positionTime, null, referencePosition.getLocationName());
      addError(getIllegalPrefix(), "the feedback list is null");
    }catch (IllegalArgumentException exception){}
    try {
      adaptiveFeedback = new AdaptiveFeedback(positionTime, feedbackList, null);
      addError(getIllegalPrefix(), "the reference position is null");
    }catch (IllegalArgumentException exception){}
  }

  /**
   * Tests if the constructor works with valid input.
   */
  @Test
  @DisplayName("Tests if the constructor works with valid input")
  public void testConstructorWorksWithValidInput(){
    float positionTime = 1f;
    List<CategoryFeedback> feedbackList = new ArrayList<>();
    ReferencePosition referencePosition = new ReferencePosition(0, "Seat 1");
    try {
      AdaptiveFeedback adaptiveFeedback =  new AdaptiveFeedback(positionTime, feedbackList, referencePosition.getLocationName());
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected the adaptive feedback to be made", "since the input is valid", exception);
    }
  }
}
