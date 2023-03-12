package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;

import java.util.List;

public interface SessionRegister {
    /**
     * Gets all sessions
     * @return list of sessions
     */
    List<Session> getAllSessions();


    /**
     * Adds session to list
     * @param session Session
     * @throws CouldNotAddSessionException gets thrown when session does not get added
     */
    void addSession(Session session) throws CouldNotAddSessionException;

    /**
     * Removes session from list
     * @param session Session
     * @throws CouldNotRemoveSessionException gets thrown if session does not get removed
     */
    void removeSession(Session session) throws CouldNotRemoveSessionException;

    /**
     * Removes session by id
     * @param sessionID long
     * @throws CouldNotRemoveSessionException gets thrown if session is not in system
     */
    void removeSessionByID(long sessionID) throws CouldNotRemoveSessionException;



    /**
     * Gets session by ID
     * @param sessionID long
     * @return session to get
     * @throws CouldNotGetSessionException gets thrown if session could not be found
     */
    Session getSessionById(long sessionID) throws CouldNotGetSessionException;

    /**
     * Checks if register has session
     * @return true if it has sessions, false if not
     */
    boolean CheckIfRegisterHasSession();

}
