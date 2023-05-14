package no.ntnu.ETIVR.security;

import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Represents a security service that can get the user based on their username.
 */
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
   * Loads a user based on their username.
   * @param username the username.
   * @return the user matching that username.
   * @throws UsernameNotFoundException gets thrown if the username does not match a user in the register.
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
