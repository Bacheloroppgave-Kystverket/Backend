package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.security.LoggedInUser;
import no.ntnu.ETIVR.security.SecurityService;
import no.ntnu.ETIVR.security.extra.TokenRequest;
import no.ntnu.ETIVR.security.extra.JsonToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@RequestMapping("/authenticate")
@CrossOrigin
public class TokenController {

    private AuthenticationManager authenticationManager;

    private SecurityService userDetailsService;

    public static final long totalTime = 8 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Makes an instance of the token controller class
     * @param authenticationManager AuthenticationManager
     * @param securityService SecurityService
     */
    public TokenController(AuthenticationManager authenticationManager, SecurityService securityService){
        checkIfObjectIsNull(authenticationManager, "authentication manager");
        checkIfObjectIsNull(securityService, "security service");
        this.authenticationManager = authenticationManager;
        this.userDetailsService = securityService;
    }

    /**
     * Creates authentication token
     * @param body String
     * @return !TODO what does this return?
     * @throws CouldNotGetUserException gets thrown if user could not be found
     * @throws JsonProcessingException gets thrown if Json could not be processed
     */
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody String body) throws CouldNotGetUserException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        TokenRequest authenticationRequest = objectMapper.readValue(body, TokenRequest.class);

        LoggedInUser userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if(userDetails.checkPassword(authenticationRequest.getPassword())){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
            ResponseEntity<JsonToken> response = ResponseEntity.status(HttpStatus.OK).body(new JsonToken(generateTokenForUser(userDetails.getUsername())));
            return response;
        }else{
            throw new CouldNotGetUserException("The passwords does not match");
        }

    }

    /**
     * Handles the exceptions that can occur in this controller.
     * @param exception the exception.
     * @return the response to the matching exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception exception){
        ResponseEntity<String> responseEntity;
        if(exception instanceof IllegalArgumentException || exception instanceof JsonProcessingException){
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Json file is invalid format");
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body("Username or password is invalid.");
        }
        return responseEntity;
    }


    /**
     * Generates a token for the user.
     * @param username the username.
     * @return the json web token.
     */
    private String generateTokenForUser(String username) {
        Date currentDate = new Date();
        return Jwts.builder().setClaims(new HashMap<>()).setSubject(username).setIssuedAt(currentDate)
                .setExpiration(new Date(System.currentTimeMillis() + totalTime * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
