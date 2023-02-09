package no.ntnu.EIVTR;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SessionService {
    private SessionRegister sessionRegister;

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
     *
     * @param session
     * @return
     */
    public boolean deleteSession(Session session) {
        boolean deleted = false;
        if(session != null) {
            sessionRegister.delete(session);
            deleted = true;
        }
        return deleted;
    }



}
