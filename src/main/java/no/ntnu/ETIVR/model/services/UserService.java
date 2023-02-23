package no.ntnu.ETIVR.model.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.repository.UserRepository;

public class UserService implements UserRegister {

    private UserRepository userRepository;

    /**
     *
     * @param userRepository
     */
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //Todo: Arver dokumentasjon fra interfacet.
    @Override
    public List<User> getAllUsers(){
        ///todo: Ikke cast om du ikke må. dette kan føre til mange nasty bugs. Dette er casting: (List<User>)
        return (List<User>) userRepository.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public User findUserById(int id){
        //Optional<User> user = userRepository.findById(id);
        //return user.get();
        return null;
    }



    //Todo: Jeg gjorde denne metoden for å vise deg.
    @Override
    public void addNewUser(User user) throws CouldNotAddUserException {
        checkIfObjectIsNull(user, "user");
        if(userRepository.findById(user.getUserId()).isEmpty()){
            userRepository.save(user);
        }else{
            throw new CouldNotAddUserException("There is a user with that id in the system");
        }
    }


    public void removeUserWithId(int userId) {
        //Optional<User> user = userRepository.findById(userId);
        //user.ifPresent(value -> userRepository.delete(value));

    }

    @Override
    public Optional<User> findUserByID(int userId) {
        return Optional.empty();
    }

    @Override
    public User findAll() {
        return null;
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
