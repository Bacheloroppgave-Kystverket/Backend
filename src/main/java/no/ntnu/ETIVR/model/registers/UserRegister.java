package no.ntnu.ETIVR.model.registers;

import java.util.List;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveUserException;

public interface UserRegister {
    
    /**
     * Gets all users.
     * @return returns all users
     */
    List<User> getAllUsers();

    /**
     * Adds a new user to the register.
     * @param user the new user.
     * @throws CouldNotAddUserException gets thrown if the user could not be added.
     */
    void addNewUser(User user) throws CouldNotAddUserException;


    /**
     * Finding the user by id.
     * @param userId the userId to find the user.
     * @return returns the user.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     */
    User findUserById(long userId) throws CouldNotGetUserException;



    /**
     * Removes User wit ID. and throws exception
     * @param userId
     * @throws CouldNotRemoveUserException
     */
    void removeUserWithId(long userId) throws CouldNotRemoveUserException;




}



