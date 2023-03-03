package no.ntnu.ETIVR.model.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
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
        ///todo: Ikke cast om du ikke må. dette kan føre til mange nasty bugs. Dette er casting: (List<User>)
        return (List<User>) userRepository.findAll();
    }


    @Override
    public User findUserById(long userId){
        Optional<User> user = userRepository.findById(userId);
        return user.get();
    }


    @Override
    public void addNewUser(User user) throws CouldNotAddUserException {
        checkIfObjectIsNull(user, "user");
        if(userRepository.findById(user.getUserId()).isEmpty()){
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
