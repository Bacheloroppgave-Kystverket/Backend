package no.ntnu.ETIVR.model.repository;

import java.util.Optional;
import no.ntnu.ETIVR.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for user
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


  @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
  Optional<User> findUserWithUsername(@Param("username") String nameOfSetup);
}
