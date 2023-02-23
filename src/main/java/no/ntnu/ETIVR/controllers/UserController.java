package no.ntnu.ETIVR.controllers;

import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class


UserController {

    private final UserService userService;


    /**
     *
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping

    /**
     *
     */
    public List<User> getUser() {
        return userService.getAllUsers();
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<User> getOneUser(@PathParam("user") @PathVariable("id") int userId){
        ResponseEntity<User> response;
        User user = userService.findUserById(userId);
        if(user != null){
            response = new ResponseEntity<>(user, HttpStatus.OK);

        }else {
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
        if(userService.addNewUser(user)){
            response = new ResponseEntity<>(HttpStatus.OK);

        } else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     *
     * @param userId
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int userId){
        userService.deleteUser(userId);
    }



}

