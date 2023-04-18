package no.ntnu.ETIVR.security.extra;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import no.ntnu.ETIVR.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class RequestHeaderToken {

    private String token;

    private Claims claims;

    /**
     * Makes an instance of the JwtToken class.
     */
    public RequestHeaderToken(String requestHeader, String secret) {
        checkString(requestHeader, "request header");
        String[] splitHeader = requestHeader.split(" ");
        if(splitHeader[0].equalsIgnoreCase("bearer")){
            this.token = splitHeader[1];
            checkString(token, "token");
            this.claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }else{
            throw new IllegalArgumentException("The format of the token is invalid");
        }
    }

    /**
     * Gets the username of this token.
     * @return the username.
     */
    public String getUsername(){
        return claims.getSubject();
    }

    /**
     * Checks if the current token is expired.
     * @return <code>true</code> if the token is expired. <code>False</code> otherwise.
     */
    public boolean isTokenExpired(){
        return claims.getExpiration().before(new Date());
    }

    /**
     * Checks if the username matches.
     * @param userDetails the user details.
     * @return <code>true</code> if the usernames matches. False otherwise.
     */
    public boolean checkIfUsernameMatches(UserDetails userDetails){
        return userDetails.getUsername().equals(this.getUsername());
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
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
