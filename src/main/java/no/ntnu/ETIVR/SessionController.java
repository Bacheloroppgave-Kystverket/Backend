package no.ntnu.ETIVR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {
   /* SessionService sessionService;

    *//**
     * Constructor with parameters
     * @param sessionService session service
     *//*
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    *//**
     * Get all sessions in list
     * @return all sessions in list
     *//*
    public List<Session> getAll() {
        return sessionService.getAll();
    }

    *//**
     * Get one session in a list by id
     * @param id Integer
     * @return Http
     *//*
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


    *//**
     * Add session
     * @param session Session
     * @return HTTP status ok, or bad request if not added
     *//*
    public ResponseEntity<String> add(@RequestBody Session session) {
        ResponseEntity<String> response;
        if ( sessionService.addNewSession(session)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }


    *//**
     * Delete session
     * @param session Session
     * @return HTTP status ok, or bad request if not added
     *//*
    public ResponseEntity<String> delete(@PathVariable Session session) {
        ResponseEntity<String> response;
        if (sessionService.deleteSession(session)) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }*/
}
