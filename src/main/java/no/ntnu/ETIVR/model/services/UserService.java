package no.ntnu.ETIVR.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserRegister {

    private final UserRepository userRepository;

    /**
     * Makes an instance of the UserService class.
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }


    @Override
    public User findUserById(long userId) throws CouldNotGetUserException {
        if(userId < 0){
            throw new IllegalArgumentException("UserId can not be less then 0");
        }
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new CouldNotGetUserException("User is not found");
        }
        return user.get();
    }

    @Override
    public User findUserByUsername(String username) throws CouldNotGetUserException {
        checkString(username, "username");
        Optional<User> user = userRepository.findUserWithUsername(username);
        if(user.isEmpty()){
            throw new CouldNotGetUserException("User is not found");
        }
        return user.get();
    }


    @Override
    public void addNewUser(User user) throws CouldNotAddUserException {
        checkIfObjectIsNull(user, "user");
        if(userRepository.findById(user.getUserId()).isEmpty() &&
            !userRepository.findUserWithUsername(user.getUserName()).isPresent()){
            userRepository.save(user);
        }else{
            throw new CouldNotAddUserException("There is a user with that id in the system");
        }
    }


    @Override
    public void removeUserWithId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> userRepository.delete(value));

    }

    /**
     * Checks if a string is of a valid format or not.
     *
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

}
