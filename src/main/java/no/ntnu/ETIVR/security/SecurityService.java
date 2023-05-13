package no.ntnu.ETIVR.security;

import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityService implements UserDetailsService {

    private UserService userService;

  /**
   * Makes an instance of the SecurityJPA class.
   * @param userService the userservice
   */
  public SecurityService(UserService userService) {
      checkIfObjectIsNull(userService, "user service");
      this.userService = userService;
  }

  /**
   * Checks if a string is of a valid format or not.
   *
   * @param stringToCheck the string you want to check.
   * @param errorPrefix   the error the exception should have if the string is invalid.
   * @throws IllegalArgumentException gets thrown if the string to check is empty or null.
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
   * @throws IllegalArgumentException gets thrown if the object is null.
   */
  private void checkIfObjectIsNull(Object object, String error) {
    if (object == null) {
      throw new IllegalArgumentException("The " + error + " cannot be null.");
    }
  }

  /**
   * !TODO documentation on this
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
    @Override
    public LoggedInUser loadUserByUsername(String username) throws UsernameNotFoundException {
      LoggedInUser userDetails;
      try {

           userDetails = new LoggedInUser(userService.findUserByUsername(username));
      }catch (CouldNotGetUserException exception){
          throw new UsernameNotFoundException(exception.getMessage());
      }
      return userDetails;
    }
}
