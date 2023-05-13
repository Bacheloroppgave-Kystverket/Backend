package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for session
 */
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
