package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;
import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.repository.SessionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/session")
public class SessionController {
    private final SessionRegister sessionRegister;

    /**
     * Constructor with parameters
     * @param sessionRepository session repository
     */
    public SessionController(SessionRepository sessionRepository) {
        sessionRegister = new SessionService(sessionRepository);
    }

    /**
     * Get all sessions
     * @return list of all sessions
     */
    public List<Session> getAllSession() {
        return sessionRegister.getAllSessions();
    }

    /**
     * Add session
     * @param body String
     */
    public void add(@RequestBody String body) throws CouldNotAddSessionException, JsonProcessingException {
        sessionRegister.addSession(makeSessionFromJson(body));
    }

    /**
     * Delete session
     * @param session Session
     * @return HTTP status ok, or bad request if not added
     */
    public void delete(@RequestParam(value = "session") Session session) throws CouldNotRemoveSessionException  {
        sessionRegister.removeSession(session);
    }

    private Session makeSessionFromJson(String body) throws  JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, Session.class);
    }

    /**
     * Checks if session is taken
     * @param sessionToCheck session to check String
     * @return whether the session is taken
     */
    public boolean getIfSessionIsTaken(@RequestParam(value= "sessionToCheck") String sessionToCheck) {
        checkString(sessionToCheck);
        return sessionRegister.CheckIfRegisterHasSession();
    }

    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     *
     */
    private void checkString(String stringToCheck){
        checkIfObjectIsNull(stringToCheck, "session");
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + "session" + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
        if (object == null){
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
