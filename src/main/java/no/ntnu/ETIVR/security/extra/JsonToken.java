package no.ntnu.ETIVR.security.extra;

/**
 * Is a class that represnets a json token that is sent to the user.
 */
public class JsonToken {


    private final String token;

    /**
     * Makes an instance of the JsonToken class.
     * @param token the token.
     */
    public JsonToken(String token) {
        checkString(token, "jwttoken");
        this.token = token;
    }

    /**
     * Gets the token.
     * @return the token.
     */
    public String getToken() {
        return this.token;
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
