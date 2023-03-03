package no.ntnu.ETIVR.model;

import java.util.ArrayList;

import java.util.List;
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
    try {
      adaptiveFeedback =  new AdaptiveFeedback(null, positionTime, feedbackList);
      addError(getIllegalPrefix(), "the input position name is null");
    }catch (IllegalArgumentException exception){}
    try {
      adaptiveFeedback = new AdaptiveFeedback("", positionTime, feedbackList);
      addError(getIllegalPrefix(), "the input position name is empty");
    }catch (IllegalArgumentException exception){}
    try {
      adaptiveFeedback = new AdaptiveFeedback(positionName, -2, feedbackList);
      addError(getIllegalPrefix(), "the position time is negative");
    }catch (IllegalArgumentException exception){}
    try {
      adaptiveFeedback = new AdaptiveFeedback(positionName, positionTime, null);
      addError(getIllegalPrefix(), "the feedback list is null");
    }catch (IllegalArgumentException exception){}
  }

  /**
   * Tests if the constructor works with valid input.
   */
  @Test
  @DisplayName("Tests if the constructor works with valid input")
  public void testConstructorWorksWithValidInput(){
    String positionName = "Seat 1";
    float positionTime = 1f;
    List<CategoryFeedback> feedbackList = new ArrayList<>();
    try {
      AdaptiveFeedback adaptiveFeedback =  new AdaptiveFeedback(positionName, positionTime, feedbackList);
    }catch (IllegalArgumentException exception){
      addErrorWithException("Expected the adaptive feedback to be made", "since the input is valid", exception);
    }
  }
}
