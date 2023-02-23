package no.ntnu.ETIVR.model.registers;

import java.util.List;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveUserException;

public interface UserRegister {


    /**
     * @return
     */
    List<User> getAllUsers();

    /**
     * Adds a new user to the register.
     * @param user the new user.
     * @throws CouldNotAddUserException gets thrown if the user could not be added.
     */
    void addNewUser(User user) throws CouldNotAddUserException;


    Optional<User> findUserByID(int userId) throws CouldNotGetUserException;

    /**
     * @param userId
     * @return
     */
    void removeUserWithId(int userId) throws CouldNotRemoveUserException;

    /
    User findAll();

}



