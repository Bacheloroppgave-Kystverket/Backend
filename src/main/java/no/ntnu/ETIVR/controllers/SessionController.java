package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Logger;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSessionException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSessionException;
import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.services.SessionService;
import no.ntnu.ETIVR.model.registers.SessionRegister;
import no.ntnu.ETIVR.model.repository.SessionRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/session")
@CrossOrigin
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
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Session> getAllPersistedSessions(@RequestParam(value = "simulationSetupName", required = false) ArrayList<String> simulationSetupNames,
                                                 @RequestParam(value = "username", required = false) ArrayList<String> usernames,
                                                 @RequestParam(value = "startDate", required = false) String startDate,
                                                 @RequestParam(value = "endDate", required = false) String endDate){
        LocalDate start = startDate != null && !startDate.isBlank() ? makeDate(startDate) : null;
        LocalDate stop = endDate != null && !endDate.isBlank() ? makeDate(endDate) : null;
        boolean validSimulationSetupName = simulationSetupNames!= null && !simulationSetupNames.isEmpty();
        boolean validUsername = usernames != null && !usernames.isEmpty();
        return sessionRegister.getAllSessions().stream().filter(session -> {
            boolean valid = !validSimulationSetupName;
            if(validSimulationSetupName){
                valid = simulationSetupNames.stream().anyMatch(name -> name.equals(session.getSimulationSetup().getNameOfSetup()) );
            }
            return valid;
        }).filter(session -> {
            boolean valid = !validUsername;
            if (validUsername) {
                valid = usernames.stream().anyMatch(username -> username.equals(session.getUser().getUserName()));
            }
            return valid;
        }).filter(session -> {
            boolean valid = start != null;
            if(valid){
                valid = session.getCurrentDate().toLocalDate().isEqual(start) || session.getCurrentDate().toLocalDate().isAfter(start);
            }else{
                valid = true;
            }
            return valid;
        }).filter(session -> {
            boolean valid = stop == null;
            if(!valid){
                valid = session.getCurrentDate().toLocalDate().isEqual(stop) || session.getCurrentDate().toLocalDate().isBefore(stop);
            }else{
                valid = true;
            }
            return valid;
        }).toList();
    }

    /**
     * Makes the string into a local date.
     * @param dateAsString the date as string.
     * @return the local date.
     */
    private LocalDate makeDate(String dateAsString){
        List<Integer> dateAsArray = Arrays.stream(dateAsString.split("-")).map(numberAsString -> Integer.parseInt(numberAsString)).toList();
        if(dateAsArray.size() > 3 || dateAsArray.size() < 3){
            throw new IllegalArgumentException("Invalid format on date.");
        }
        return LocalDate.of(dateAsArray.get(2), dateAsArray.get(1), dateAsArray.get(0));
    }

    /**
     * Add session
     * @param body String
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public void add(@RequestBody String body) throws CouldNotAddSessionException, JsonProcessingException {
        Session session = makeSessionFromJson(body);
        session.setCurrentDate(LocalDateTime.now());
        sessionRegister.addSession(session);
    }

    /**
     * Delete session
     * @param session Session
     * @return HTTP status ok, or bad request if not added
     */
    public void delete(@RequestParam(value = "session") Session session) throws CouldNotRemoveSessionException  {
        sessionRegister.removeSession(session);
    }

    /**
     * Make session from JSON
     * @param body String
     * @return return session from JSON
     * @throws JsonProcessingException gets thrown if trouble processing exception
     */
    private Session makeSessionFromJson(String body) throws  JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, Session.class);
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

    /**
     * Get session by ID
     * @param id long
     * @return session by Id
     * @throws CouldNotGetSessionException gets thrown if session is not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public Session getSessionById(@PathVariable("id") long id) throws CouldNotGetSessionException {
        return sessionRegister.getSessionById(id);
    }
}
