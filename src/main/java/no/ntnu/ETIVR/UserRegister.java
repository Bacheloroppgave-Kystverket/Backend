package no.ntnu.ETIVR;

import java.util.List;
import java.util.Optional;

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

    void save(User user);

    Object findAll();

}



