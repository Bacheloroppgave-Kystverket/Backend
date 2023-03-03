package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;

import java.util.List;

public interface SessionRegister {


    void addSession(Session session) throws CouldNotAddSessionException;

    void removeSession(Session session) throws CouldNotRemoveSessionException;

    Session getSessionById(long sessionID) throws CouldNotGetSessionException;

    boolean CheckIfRegisterHasSession();

    List<Session> getAllSessions();
}
