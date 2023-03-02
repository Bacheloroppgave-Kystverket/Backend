package no.ntnu.ETIVR.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

        private String userName;

        private String password;

        private boolean active = true;


        /**
         *
         */
        public User() {

        }

    /**
     * Constructor with parameters.
     * @param userId
     * @param userName
     * @param password
     */
        @JsonCreator
        public User(@JsonProperty("userId") long userId, @JsonProperty("userName") String userName, @JsonProperty("password") String password){
            checkIfNumberNotNegative(userId,"UserId");
            this.userId = userId;
            checkIfObjectIsNull(userName, "UserName");
            this.userName = userName;
            checkIfObjectIsNull(password,"Password");
            this.password = password;
        }

    /**
     * Cheking if the user is active.
     * @return returning status
     */
    public boolean isActive() {
            return active;
        }


    /**
     * Setting user to active.
     */
    public void setActive(){
            this.active = active;
        }

    /**
     * Gets the userId.
     * @return returning the userId
     */
    public long getUserId(){
            return userId;
        }

    /**
     * Sets the userId.
     */
    public void setUserId(){
            this.userId =userId;
        }

    /**
     * Gets the password of the user.
     * @return returning the password.
     */
    public String getPassword() {
            return password;
        }

    /**
     * Sets the password to a user.
     */
    public void setPassword(){
            this.password = password;
        }

    /**
     * Checks if an input date is before or equal to today's date.
     * @param localDateTime the date to check.
     */
    private void checkIfDateIsBeforeOrEqualToCurrentDate(LocalDateTime localDateTime){
        checkIfObjectIsNull(localDateTime, "date");
        if (LocalDate.now().isBefore(ChronoLocalDate.from(localDateTime))){
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
     * Checks if an int number is not negative
     * @param object int object to be checked
     * @param error String
     */
    private void checkIfIntNumberNotNegative(int object, String error) {
        if (object <= 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
        }
    }


    /**
     * Check to make sure that integer values cannot be negative.
     * @param object the object to be checked.
     * @param error exception message to be displayed.
     */
    private void checkIfNumberNotNegative(long object, String error) {
        if (object <= 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
        }
    }

}

