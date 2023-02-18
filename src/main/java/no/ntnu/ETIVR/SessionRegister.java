package no.ntnu.ETIVR;

import no.ntnu.ETIVR.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.exceptions.CouldNotRemoveSessionException;

import java.util.List;

public interface SessionRegister {
    void addSession(Session session) throws CouldNotAddSessionException;

    void removeSession(Session session) throws CouldNotRemoveSessionException;

    Session getSessionById(long sessionID) throws CouldNotGetSessionException;

    boolean CheckIfRegisterHasSession();

    List<Session> getAllSessions();
}
