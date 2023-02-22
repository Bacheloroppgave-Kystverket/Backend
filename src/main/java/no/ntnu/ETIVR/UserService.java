package no.ntnu.ETIVR;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserService implements UserRegister {

    private UserRegister userRegister;


    /**
     *
     * @param userRegister
     */
    public UserService(UserRegister userRegister){
        this.userRegister = userRegister;
    }

    /**
     *
     * @return
     */
    public List<User> getAllUsers(){
        return (List<User>) userRegister.findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public User findUserById(int id){
        Optional<User> user = userRegister.findById(id);
        return user.get();
    }



    public boolean addNewUser(User user){
        boolean added = false;
        if(user != null && user.isValid()){
            try{
                findUserById(user.getUserId());
            }catch (NoSuchElementException e){
                user.setPassword();
                userRegister.save(user);
                added = true;
            }
        }
        return added;
    }


    public boolean deleteUser(int userId) {
        Optional<User> user = userRegister.findById((int) userId);
        user.ifPresent(value -> userRegister.delete(value));
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
    public void save(User user) {

    }

    @Override
    public Object findAll() {
        return null;
    }


}
