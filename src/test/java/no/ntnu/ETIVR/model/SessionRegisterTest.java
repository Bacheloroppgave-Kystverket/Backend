package no.ntnu.ETIVR.model;

import static org.junit.jupiter.api.Assertions.fail;


import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.services.SessionService;
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

    private Session sessionInRegister;

    private final String removeException;

    private String addException;

    private String getException;

    @Autowired
    public SessionRegisterTest(SessionService sessionService) {
        super();
        this.sessionRegister = sessionService;
        checkIfObjectIsNull(sessionService, "Session Register");
        //Todo: Method to string?
        removeException = "makeExceptionString()";
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
            e.printStackTrace();
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
            //Todo: Breakpoint her.
            List<Session> sessionList = sessionRegister.getAllSessions();
            for (Session session : sessionList) {
                //Todo: Erroren skjer her. Du har ikke testa metoden her har du?
                sessionRegister.removeSession(session);
            }
        } catch (CouldNotRemoveSessionException | IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * Makes a default session
     * @return the default session.
     */
    private Session makeDefaultSession() {
        List<TrackableObject> trackableObjects = new ArrayList<>();
        List<ReferencePosition> referencePositions = new ArrayList<>();
        List<AdaptiveFeedback> adaptiveFeedbackLog = new ArrayList<>();
        trackableObjects.add(new TrackableObject("Pog", TrackableType.WALL, 5000));
        referencePositions.add(new ReferencePosition(50000, "Seat 1"));
        adaptiveFeedbackLog.add(new AdaptiveFeedback(50, new ArrayList<>(), "pog"));
        return new Session(LocalDateTime.now(), 2, trackableObjects, 50000, referencePositions, adaptiveFeedbackLog);
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
    @DisplayName("Tests if remove sesssion with id works with valid input")
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
