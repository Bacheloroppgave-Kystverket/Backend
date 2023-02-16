package no.ntnu.ETIVR;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SessionService {
    private final SessionRegister sessionRegister;

    /**
     * Constructor with parameters
     * @param sessionRegister session register
     */
    public SessionService(SessionRegister sessionRegister) {
        this.sessionRegister = sessionRegister;
    }

    /**
     * Find session by id
     * @param id int
     * @return session if found, else null
     */
    public Session findSessionById(int id) {
        Optional<Session> session = sessionRegister.findById(id);
        return session.orElse(null);
    }

    /**
     * Make iterable to list
     * @param iterable session
     * @return Add list of sessions
     */
    public List<Session> iterableToList(Iterable<Session> iterable) {
        List<Session> list = new LinkedList<>();
        iterable.forEach((list::add));
        return list;
    }

    /**
     * Get all the session in register
     * @return all sessions in register
     */
    public List<Session> getAll() {
        return iterableToList(sessionRegister.findAll());
    }

    /**
     * Add new session
     * @param session Session
     * @return true if added, false if not added
     */
    public boolean addNewSession(Session session) {
        boolean added = false;
        if(session != null) {
            sessionRegister.save(session);
            added = true;
        }
        return added;
    }

    /**
     * Delete session
     * @param session Session
     * @return true if deleted, false if not
     */
    public boolean deleteSession(Session session) {
        boolean deleted = false;
        if(session != null) {
            sessionRegister.delete(session);
            deleted = true;
        }
        return deleted;
    }

    public String updateSession(int id, Session session) {
        Session existingSession = findSessionById(id);
        String errorMessage = null;
        if (existingSession == null) {
            errorMessage = "No session with " + id + "is found";
        }
        if (session == null) {
            errorMessage = "Please check the data in the request body";

        } else if (session.getUserId() != id) {
            errorMessage = "Please check the id, it does not match";
        }
        if (errorMessage == null) {
            sessionRegister.save(session);
        }
        return errorMessage;
    }

}
