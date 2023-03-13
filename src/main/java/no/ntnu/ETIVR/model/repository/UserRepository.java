package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
