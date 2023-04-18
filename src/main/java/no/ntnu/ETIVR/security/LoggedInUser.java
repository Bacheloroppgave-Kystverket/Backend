package no.ntnu.ETIVR.security;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import no.ntnu.ETIVR.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoggedInUser implements UserDetails {

  private User user;

  private final List<GrantedAuthority> authorities = new LinkedList<>();

  /**
   * Makes an instance of the LoginUse class.
   */
  public LoggedInUser(User user) {
    checkIfObjectIsNull(user, "user");
    this.user = user;
    authorities.add(new SimpleGrantedAuthority("USER"));
  }

  /**
   * Sets the user.
   * @param user the user.
   */
  public void setUser(User user){
    checkIfObjectIsNull(user, "user");
    this.user = user;
  }

  /**
   * Checks if the passwords match.
   * @param inputPass the current password.
   * @return <code>true</code> if the passwords match. False otherwise.
   */
  public boolean checkPassword(String inputPass){
    return user.passwordsMatch(inputPass);
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return user.isActive();
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.isActive();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return user.isActive();
  }

  @Override
  public boolean isEnabled() {
    return user.isActive();
  }
}
