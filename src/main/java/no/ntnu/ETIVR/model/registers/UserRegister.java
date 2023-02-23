package no.ntnu.ETIVR.model.registers;

import java.util.List;
import java.util.Optional;
import no.ntnu.ETIVR.model.User;

public interface UserRegister {


    /**
     * @return
     */
    public List<User> getAllUsers();

    /**
     * @param user
     * @return
     */
    public boolean addNewUser(User user);

    /**
     * @param id
     * @return
     */
    public User findUserById(int id);

    /**
     * @param userId
     * @return
     */
    public boolean deleteUser(int userId);


    void delete(User value);

    Optional<User> findById(int userId);

    Object findAll();

}



