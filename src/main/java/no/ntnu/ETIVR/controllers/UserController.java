package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.services.UserService;
import no.ntnu.ETIVR.security.LoggedInUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;


    /**
     * The constructor of the class.
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }



    /**
     * Gets the users from the list.
     * @return returns all users
     */
    public List<User> getUser() {
        return userService.getAllUsers();
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getLoggedInUser(Authentication authentication){
        LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();
        return loggedInUser.getUser();
    }
    /**
     * Registers new customer
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody String body) throws JsonProcessingException {
        ResponseEntity<String> response;
        User user = new ObjectMapper().readValue(body, User.class);
        try {
            userService.addNewUser(user);
            response = new ResponseEntity<>(HttpStatus.OK);
        }catch (CouldNotAddUserException exception){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }




}

