package no.ntnu.ETIVR.model;

import static org.junit.jupiter.api.Assertions.fail;


import java.lang.ref.Reference;
import javax.swing.text.Position;
import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.*;
import no.ntnu.ETIVR.model.feedback.AdaptiveFeedback;
import no.ntnu.ETIVR.model.feedback.CategoryConfiguration;
import no.ntnu.ETIVR.model.feedback.CategoryFeedback;
import no.ntnu.ETIVR.model.feedback.PositionConfiguration;
import no.ntnu.ETIVR.model.position.PositionRecord;
import no.ntnu.ETIVR.model.position.ReferencePosition;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.trackable.GazeData;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import no.ntnu.ETIVR.model.trackable.TrackableRecord;
import no.ntnu.ETIVR.model.trackable.TrackableType;
import no.ntnu.ETIVR.model.trackable.ViewDistance;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Tests the session register.
 */
@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SessionRegisterTest extends DefaultTest implements RegisterTest{

  private final SessionRegister sessionRegister;

  private final SimulationSetupRegister simulationSetupRegister;

  private final UserRegister userRegister;

  private TrackableObjectRegister trackableObjectRegister;

  private User user;

  private Session sessionInRegister;

  private final String removeException;

  private SimulationSetup simulationSetup;

  private String addException;

  private String getException;

  @Autowired
  public SessionRegisterTest(SessionService sessionService, SimulationSetupService simulationSetupService, UserRegister userRegister, TrackableObjectsService trackableObjectRegister) {
    super();
    this.sessionRegister = sessionService;
    this.simulationSetupRegister = simulationSetupService;
    this.userRegister = userRegister;
    checkIfObjectIsNull(sessionService, "Session Register");
    removeException = makeExceptionString("remove exception");
    try {
      this.trackableObjectRegister = trackableObjectRegister;
      this.trackableObjectRegister.addTrackableObject(new TrackableObject("Pog", TrackableType.WALL, 5000));
      this.simulationSetupRegister.addSimulationSetup(makeSimulationSetup());
      this.simulationSetup = this.simulationSetupRegister.getSimulationSetups().get(0);
      userRegister.addNewUser(makeUser());
      this.user = userRegister.getAllUsers().get(0);

    }catch (IllegalArgumentException | CouldNotAddSimulationSetupException | CouldNotAddUserException | CouldNotAddTrackableObjectException e){
      fail("Data could not be added.");
    }
  }


  @Override
  @BeforeEach
  public void SetupTestData() {
    setUpStringBuilder();
    try {
      for(Session session : sessionRegister.getAllSessions()) {
        sessionRegister.removeSessionByID(session.getSessionId());
      }
      sessionRegister.addSession(makeDefaultSession());
      sessionInRegister = sessionRegister.getAllSessions().get(0);
    } catch (CouldNotRemoveSessionException | CouldNotAddSessionException | IllegalArgumentException e) {
      fail(makeCouldNotGetDefaultString("test data"));
    }
  }

  @Override
  @AfterAll
  public void checkForErrors() {checkIfTestsFailedAndDisplayResult();

  }

  @Override
  @AfterAll
  public void cleanUp() {
    try {
      List<Session> sessionList = sessionRegister.getAllSessions();
      for (Session session : sessionList) {
        sessionRegister.removeSessionByID(session.getSessionId());
      }
      for (SimulationSetup simulationSetup : simulationSetupRegister.getSimulationSetups()){
        simulationSetupRegister.removeSimulationSetup(simulationSetup);
      }
      for (User user : userRegister.getAllUsers()){
        userRegister.removeUserWithId(user.getUserId());
      }
      for (TrackableObject trackableObject : trackableObjectRegister.getAllTrackableObjects()){
        trackableObjectRegister.removeTrackableObject(trackableObject);
      }
    } catch (CouldNotRemoveSessionException | IllegalArgumentException | CouldNotRemoveSimulationSetupException | CouldNotRemoveUserException | CouldNotRemoveTrackableObjectException e) {
      e.printStackTrace();
    }
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

  private ReferencePosition makeReferencePosition(){
    return new ReferencePosition(50000, "Seat 1", makePositionConfiguration());
  }

  /**
   * Makes a default user.
   * @return the default user.
   */
  private User makeUser(){
    return new User(1, "hei", "pass");
  }

  /**
   * Makes a default simulation setup.
   * @return the simulation setup.
   */
  private SimulationSetup makeSimulationSetup(){
    List<TrackableObject> trackableObjects = trackableObjectRegister.getAllTrackableObjects();
    List<ReferencePosition> referencePositions = new ArrayList<>();
    referencePositions.add(makeReferencePosition());
    return new SimulationSetup("Hei", trackableObjects, referencePositions);
  }

  /**
   * Makes the trackable records.
   * @param simulationSetup the simulation setup.
   * @return the trackable records.
   */
  private List<TrackableRecord> makeTrackableRecords(SimulationSetup simulationSetup){
    List<GazeData> gazeData = new ArrayList<>();
    gazeData.add(new GazeData(1,1,simulationSetup.getReferencePositions().get(0)));
    return simulationSetup.getCloseTrackableObjects().stream().map(trackableObject -> new TrackableRecord(gazeData, ViewDistance.CLOSE, trackableObject)).toList();
  }

  /**
   * Makes a default position records.
   * @param simulationSetup the simulation setup.
   * @return the list of the simulation setups.
   */
  private List<PositionRecord> makePositionRecords(SimulationSetup simulationSetup){
    List<PositionRecord> positionRecords = new ArrayList<>();
    List<AdaptiveFeedback> adaptiveFeedbacks = new ArrayList<>();
    ReferencePosition referencePosition = simulationSetup.getReferencePositions().get(0);
    List<CategoryFeedback> categoryFeedbacks = new ArrayList<>();
    categoryFeedbacks.add(new CategoryFeedback(TrackableType.OTHER, 1));
    adaptiveFeedbacks.add(new AdaptiveFeedback(1, categoryFeedbacks));
    positionRecords.add(new PositionRecord(referencePosition,1,  adaptiveFeedbacks));
    return positionRecords;
  }

  /**
   * Makes a default session
   * @return the default session.
   */
  private Session makeDefaultSession() {
    SimulationSetup simulationSetup = this.simulationSetup;
    return new Session(LocalDateTime.now(), this.user ,500000, makeTrackableRecords(simulationSetup), makePositionRecords(simulationSetup),simulationSetup);
  }

  @DisplayName("Tests if add session works with valid input")
  @Test
  public void testIfAddSessionWorksWithValidInput() {
    try {
      sessionRegister.addSession(makeDefaultSession());
    } catch (CouldNotAddSessionException | IllegalArgumentException e) {
      addErrorWithException("Expected the session to be added", "since the input is valid", e);
    }
  }

  @DisplayName("Tests if add session works with invalid input")
  @Test
  public void testIfAddSessionWorksWithInvalidInput() {
    try {
      sessionRegister.addSession(null);
      addError(getIllegalPrefix(), "the input is null");
    } catch (IllegalArgumentException e) {

    } catch (CouldNotAddSessionException e) {
      addErrorWithException(getIllegalPrefix(), "the input is null", e);
    }
    try {
      sessionRegister.addSession(sessionInRegister);
    } catch (IllegalArgumentException e) {
      addErrorWithException(addException, "the input is already in the register", e);
    } catch (CouldNotAddSessionException e) {
    }
  }

  @Test
  @DisplayName("Tests if remove session works with valid input")
  public void testIfRemoveSessionWorksWithValidInput() {
    try {
      sessionRegister.removeSession(sessionInRegister);
    } catch (CouldNotRemoveSessionException | IllegalArgumentException e) {
      addErrorWithException("Expected the session to be removed", "since the input is valid", e);
    }
  }


  @Test
  @DisplayName("Tests if remove session works with invalid input")
  public void testIfRemoveSessionWorksWithInvalidInput() {
    try {
      sessionRegister.removeSession(null);
      addError(getIllegalPrefix(), "the input is null");
    } catch (IllegalArgumentException e) {}
    catch (CouldNotRemoveSessionException e) {
      addErrorWithException(getIllegalPrefix(), "the input is null", e);
    }
    try {
      sessionRegister.removeSession(makeDefaultSession());
      addError(getIllegalPrefix(), "the input object is not in the register");
    } catch (IllegalArgumentException e) {
      addErrorWithException(removeException, "the input object is not in the register", e);
    } catch (CouldNotRemoveSessionException e) {}
  }

  @Test
  @DisplayName("Tests if remove session with id works with valid input")
  public void testIfRemoveSessionWithIdWorksWithValidInput() {
    try {
      sessionRegister.removeSessionByID(sessionInRegister.getSessionId());
    } catch (IllegalArgumentException | CouldNotRemoveSessionException e) {
      addErrorWithException("Expected the object to be removed", "since its in the register", e);
    }
  }

  @Test
  @DisplayName("Tests if remove sessions with id works with invalid input")
  public void testIfRemoveSessionsWithIdWorksWithInvalidInput() {
    try {
      sessionRegister.removeSessionByID(-2);
      addError(getIllegalPrefix(), "the input is negative");
    } catch (IllegalArgumentException e) {}
    catch (CouldNotRemoveSessionException e) {
      addErrorWithException(getIllegalPrefix(), "the input is negative", e);
    } try {
      sessionRegister.removeSessionByID(60000);
      addError(removeException, "the input is not in the register");
    } catch (IllegalArgumentException e) {
      addErrorWithException(removeException, "the input is not in the register", e);
    } catch (CouldNotRemoveSessionException e) {}
  }

  @Test
  @DisplayName("Tests if get session works with valid input.")
  public void testsIfGetSessionWorksWithValidInput() {
    try {
      sessionRegister.getSessionById(sessionInRegister.getSessionId());
    } catch (IllegalArgumentException | CouldNotGetSessionException e) {
      addErrorWithException("Expected the session to be received since", "the input is valid", e);
    }
  }

  @Test
  @DisplayName("Tests if get session works with invalid input.")
  public void testIfGetSessionWorksWithInvalidInput() {
    try {
      sessionRegister.getSessionById(-2);
      addError(getIllegalPrefix(), "the input is negative");
    } catch (IllegalArgumentException e) {}
    catch (CouldNotGetSessionException e) {
      addErrorWithException(getIllegalPrefix(), "the input is negative", e);
    } try {
      sessionRegister.getSessionById(50000);
      addError(getIllegalPrefix(), "the input is not in the register");
    } catch (IllegalArgumentException e) {
      addErrorWithException(getException, "the input is in the register", e);
    } catch (CouldNotGetSessionException e) {}
  }



  /**
   * Checks if an object is null.
   * @param object the object you want to check.
   * @param error  the error message the exception should have.
   * @throws IllegalArgumentException gets thrown if the object is null.
   */
  private void checkIfObjectIsNull(Object object, String error) {
    if (object == null) {
      throw new IllegalArgumentException("The " + error + " cannot be null.");
    }
  }
}
