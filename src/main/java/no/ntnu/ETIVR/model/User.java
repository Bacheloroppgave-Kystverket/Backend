package no.ntnu.ETIVR.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
     *
     */
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


        @JsonCreator
        public User(@JsonProperty("userId") int userId, @JsonProperty("userName") String userName, @JsonProperty("password") String password){
            this.userId = userId;
            this.userName = userName;
            this.password = password;
        }

        /**
         *
         */
        public boolean isActive() {
            return active;
        }

        /**
         *
         */
        public void setActive(){
            this.active = active;
        }

        /**
         *
         * @return
         */
        public long getUserId(){
            return userId;
        }

        /**
         *
         */
        public void setUserId(){
            this.userId =userId;
        }

        /**
         *
         * @return
         */
        public String getPassword() {
            return password;
        }

        /**
         *
         */
        public void setPassword(){
            this.password = password;
        }
    }

