package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.Session;
import no.ntnu.ETIVR.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
