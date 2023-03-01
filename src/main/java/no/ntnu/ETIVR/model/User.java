package no.ntnu.ETIVR.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;


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
            this.userId = userId;
            this.userName = userName;
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
    }

