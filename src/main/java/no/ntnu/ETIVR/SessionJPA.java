package no.ntnu.ETIVR;

import no.ntnu.ETIVR.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.exceptions.CouldNotRemoveSessionException;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SessionJPA implements SessionRegister{
    private final SessionRepository sessionRepository;

    /**
     * Makes an instance of the SessionJPA class
     * @param sessionRepository the session repository
     */
    public SessionJPA(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void addSession(Session session) throws CouldNotAddSessionException {
        checkIfObjectIsNull(session,"session");
        if (!sessionRepository.existsById(session.getSessionId())) {
            sessionRepository.save(session);
        } else {
            throw new CouldNotAddSessionException("Session with id " + session.getSessionId() + " is already in the system.");
        }
    }

    @Override
    public void removeSession(Session session) throws CouldNotRemoveSessionException {
        checkIfObjectIsNull(session, "session");
        if (!sessionRepository.existsById(session.getSessionId())) {
            sessionRepository.delete(session);
        } else {
            throw new CouldNotRemoveSessionException("The session with id " + session.getSessionId() + "could not be located in the system");
        }
    }

    @Override
    public Session getSessionById(long sessionID) throws CouldNotGetSessionException {
        checkIfObjectIsNull(sessionID, "session ID");
        Optional<Session> optionalSession = sessionRepository.findById(sessionID);
        if (optionalSession.isEmpty()) {
            throw new CouldNotGetSessionException("The session with id " + sessionID + " is not in the system");
        } return optionalSession.get();

    }

    @Override
    public boolean CheckIfRegisterHasSession() {
        return false;
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
}
