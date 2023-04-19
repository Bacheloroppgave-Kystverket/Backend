package no.ntnu.ETIVR.controllers;

import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
     * Gets one User by id.
     * @param userId the userid to find one user
     * @return the response if they find a user or not.
     */
    @GetMapping
    public ResponseEntity<User> getOneUser(@PathParam("user") @PathVariable("id") int userId){
        ResponseEntity<User> response;

        try{
            User user = userService.findUserById(userId);
            response = new ResponseEntity<>(user, HttpStatus.OK);
        }catch (CouldNotGetUserException exception){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * Registers new customer
     * @param user
     * @return
     */
    @PostMapping(consumes  ="application/json")
    public ResponseEntity<String> registerNewUser(@RequestBody User user){
        ResponseEntity<String> response;
        try {
            userService.addNewUser(user);
            response = new ResponseEntity<>(HttpStatus.OK);
        }catch (CouldNotAddUserException exception){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Deletes a user by userId
     * @param userId finding the id of the user you want to delete.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long userId){
        userService.removeUserWithId(userId);
    }



}

