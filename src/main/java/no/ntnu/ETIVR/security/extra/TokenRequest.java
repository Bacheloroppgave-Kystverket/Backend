package no.ntnu.ETIVR.security.extra;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a token request that is sent to the server.
 */
public class TokenRequest {

    private String username;

    private String password;

    /**
     * Makes an instance of the TokenRequest.
     * @param username the username.
     * @param password the password.
     */
    public TokenRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        checkString(username, "username");
        checkString(password, "password");
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     * @return the username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the password.
     * @return the password.
     */
    public String getPassword() {
        return this.password;
    }


    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix   the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
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
