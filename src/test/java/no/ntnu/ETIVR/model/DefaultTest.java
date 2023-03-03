package no.ntnu.ETIVR.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public abstract class DefaultTest {

  private int errors;

  private StringBuilder stringBuilder;

  private String illegalPrefix;

  /**
   * Makes an instance of Default test
   */
  public DefaultTest(){
    illegalPrefix = makeExceptionString("IllegalArgumentException");
  }

  /**
   * Gets the illegal prefix.
   * @return the illegal prefix
   */
  protected String getIllegalPrefix(){
    return illegalPrefix;
  }


  /**
   * Sets up a test cart.
   */
  protected void setUpStringBuilder(){
    this.stringBuilder = new StringBuilder();
    errors = 0;
  }

  /**
   * Sets up the testing session.
   */
  public abstract void SetupTestData();

  /**
   * Checks for errors in the tests.
   */
  public abstract void checkForErrors();


  /**
   * Checks if the tests failed and displays the results.
   */
  protected void checkIfTestsFailedAndDisplayResult(){
    if(stringBuilder.length() == 0){
      assertTrue(true);
    }else {
      fail("\nAmount of errors " + errors + " listed errors: " + stringBuilder.toString());
    }
  }


  /**
   * Adds an error with an exception in the title.
   * @param errorPrefix what it should say before the main error.
   * @param error what it should say after the error.
   * @param exception the exception that was not expected.
   */
  protected void addErrorWithException(String errorPrefix, String error, Exception exception){
    addError(errorPrefix, error);
    stringBuilder.append(" and not a ").append(exception.getClass().getSimpleName());
  }

  /**
   * Makes an exception into the wanted string.
   * @param exceptionName the name of the exception.
   * @return the full exception string.
   */
  protected String makeExceptionString(String exceptionName){
    return "Expected to get a " +  exceptionName + " since";
  }

  /**
   * Adds a new error to the string builder.
   * @param errorPrefix what it should say before the error.
   * @param error the error to append.
   */
  protected void addError(String errorPrefix, String error){
    stringBuilder.append("\n").append(errorPrefix).append(" ").append(error);
    errors++;
  }

  /**
   * Makes a could not get default string.
   * @param defaultString the default string
   * @return the new could not get default string.
   */
  protected String makeCouldNotGetDefaultString(String defaultString){
    return "Default " + defaultString + " could not be added. ";
  }
}
