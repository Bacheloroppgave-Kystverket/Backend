package no.ntnu.ETIVR.model.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;
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
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }



    public boolean addNewUser(User user){
        boolean added = false;
        if(user != null && user.isValid()){
            try{
                findUserById(user.getUserId());
            }catch (NoSuchElementException e){
                user.setPassword();
                userRepository.save(user);
                added = true;
            }
        }
        return added;
    }


    public boolean deleteUser(int userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> userRepository.delete(value));
        return user.isPresent();
    }


    @Override
    public void delete(User value) {

    }

    @Override
    public Optional<User> findById(int userId) {
        return Optional.empty();
    }

    @Override
    public User findAll() {
        return null;
    }


}
