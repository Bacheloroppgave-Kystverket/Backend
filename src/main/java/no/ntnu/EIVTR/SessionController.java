package no.ntnu.EIVTR;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class SessionController {
    SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    public List<Session> getAll() {
        return sessionService.getAll();
    }

    public ResponseEntity<Session> getOne(@PathVariable Integer id) {
        ResponseEntity<Session> response;
        Session session = sessionService.findSessionById(id);
        if(session != null) {
            response = new ResponseEntity<>(session, HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    public ResponseEntity<String> add(@RequestBody Session session) {
        ResponseEntity<String> response;
        if ( sessionService.addNewSession(session)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public ResponseEntity<String> delete(@PathVariable Session session) {
        ResponseEntity<String> response;
        if (sessionService.deleteSession(session)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
