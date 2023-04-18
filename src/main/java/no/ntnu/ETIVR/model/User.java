package no.ntnu.ETIVR.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;


@Entity(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private long userId;

  private String username;

  private String password;

  private boolean active = true;


  /**
   *
   */
  public User() {

  }

  /**
   * Constructor with parameters.
   *
   * @param userId
   * @param username
   * @param password
   */
  @JsonCreator
  public User(@JsonProperty("userId") long userId, @JsonProperty("username") String username,
              @JsonProperty("password") String password) {
    checkIfNumberNotNegative(userId, "UserId");
    this.userId = userId;
    checkIfObjectIsNull(username, "UserName");
    this.username = username;
    checkIfObjectIsNull(password, "Password");
    this.password = new BCryptPasswordEncoder().encode(password);
  }

  /**
   * Cheking if the user is active.
   *
   * @return returning status
   */
  public boolean isActive() {
    return active;
  }


  /**
   * Setting user to active.
   */
  public void setActive() {
    this.active = active;
  }

  /**
   * Gets the userId.
   *
   * @return returning the userId
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Sets the userId.
   */
  public void setUserId() {
  }

  /**
   * Gets the password of the user.
   *
   * @return returning the password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password to a user.
   */
  public void setPassword() {
    this.password = password;
  }

  /**
   * Checks if an input date is before or equal to today's date.
   *
   * @param localDateTime the date to check.
   */
  private void checkIfDateIsBeforeOrEqualToCurrentDate(LocalDateTime localDateTime) {
    checkIfObjectIsNull(localDateTime, "date");
    if (LocalDate.now().isBefore(ChronoLocalDate.from(localDateTime))) {
      throw new IllegalArgumentException("The set date is after current date.");
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

  /**
   * Check to make sure that integer values cannot be negative.
   *
   * @param object the object to be checked.
   * @param error  exception message to be displayed.
   */
  private void checkIfNumberNotNegative(long object, String error) {
    if (object < 0) {
      throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
    }
  }

  /**
   * Get username
   * @return username
   */
  public String getUserName() {
    return username;
  }

  /**
   * Checks if the passwords match.
   * @param currentPassword the current password.
   * @return <code>true</code> if the passwords match. False otherwise.
   */
  public boolean passwordsMatch(String currentPassword){
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(currentPassword, password);
  }
}

