package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
}
