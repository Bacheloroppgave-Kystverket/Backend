package no.ntnu.ETIVR.model.services;

import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.repository.SessionRepository;
import org.springframework.stereotype.Service;


/**
 * Represents the JPA service of Session.
 */
@Service
public class SessionService implements SessionRegister {
    private final SessionRepository sessionRepository;

    /**
     * Makes an instance of the SessionService class
     * @param sessionRepository the session repository
     */
    public SessionService(SessionRepository sessionRepository) {
        checkIfObjectIsNull(sessionRepository, "session repository");
        this.sessionRepository = sessionRepository;
    }

    /**
     * Adds session
     * @param session Session
     * @throws CouldNotAddSessionException gets thrown if session could not be added
     */
    @Override
    public void addSession(Session session) throws CouldNotAddSessionException {
        checkIfSessionIsValid(session);
        if (!sessionRepository.existsById(session.getSessionId())) {
            sessionRepository.save(session);
        } else {
            throw new CouldNotAddSessionException("Session with id " +
                    session.getSessionId() + " is already in the system.");
        }
    }


    @Override
    public void removeSession(Session session) throws CouldNotRemoveSessionException {
        checkIfSessionIsValid(session);
        if (sessionRepository.existsById(session.getSessionId())) {
            sessionRepository.deleteById(session.getSessionId());
        } else {
            throw new CouldNotRemoveSessionException("The session with id " + session.getSessionId() + "could not be located in the system");
        }
    }

    @Override
    public void removeSessionByID(long sessionID) throws CouldNotRemoveSessionException {
        checkIfNumberIsAboveZero(sessionID);
        if (sessionRepository.existsById(sessionID)) {
            sessionRepository.deleteById(sessionID);
        } else {
            throw new CouldNotRemoveSessionException("the session with the ID " + sessionID + " is not in the system");
        }
    }

    @Override
    public Session getSessionById(long sessionID) throws CouldNotGetSessionException {
        checkIfNumberIsAboveZero(sessionID);
        Optional<Session> optionalSession = sessionRepository.findById(sessionID);
        if (optionalSession.isEmpty()) {
            throw new CouldNotGetSessionException("The session with id " + sessionID + " is not in the system");
        }
        return optionalSession.get();

    }

    @Override
    public boolean CheckIfRegisterHasSession() {
        return !getAllSessions().isEmpty();
    }

    @Override
    public List<Session> getAllSessions() {
        List<Session> sessions = new LinkedList<>();
        sessionRepository.findAll().forEach(sessions::add);
        return sessions;
    }

    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }

    private void checkIfSessionIsValid(Session session) {
        checkIfObjectIsNull(session, "session");
    }

    /**
     * Checks if the input number is above zero.
     * @param numberToCheck the number to check.
     */
    private void checkIfNumberIsAboveZero(long numberToCheck) {
        if (numberToCheck <= 0) {
            throw new IllegalArgumentException("The " + "trackable object" + " must be larger than zero.");
        }
    }
}
