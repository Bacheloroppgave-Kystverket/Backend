package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.services.UserService;
import no.ntnu.ETIVR.security.LoggedInUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<String> getUsers() {
        return userService.getAllUsers().stream().map(User::getUserName).toList();
    }


    /**
     * Gets the currently logged user.
     * @param authentication the authentication.
     * @return the current user.
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getLoggedInUser(Authentication authentication){
        LoggedInUser loggedInUser = (LoggedInUser) authentication.getPrincipal();
        return loggedInUser.getUser();
    }

    /**
     * Makes it possible for a new user to register a user.
     * @param body the json body.
     * @return the response entity. Ok if the new user is added.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody String body)
            throws JsonProcessingException, CouldNotAddUserException {
        ResponseEntity<String> response;

        userService.addNewUser(makeUserFromJson(body));
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    /**
     * Makes a user from a JSON body.
     * @param body the json body.
     * @throws JsonProcessingException gets thrown if the json format is invalid.
     * @return the user.
     */
    private User makeUserFromJson(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, User.class);
    }

    /**
     * Handles the exceptions that can occur in this controller.
     * @param exception the exception.
     * @return the response to the matching exceptions.
     */
    @ExceptionHandler(CouldNotAddUserException.class)
    public ResponseEntity<String> handleExceptions(CouldNotAddUserException exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body("User with that username is already in the system");
    }
}

