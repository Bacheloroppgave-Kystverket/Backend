package no.ntnu.EIVTR;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRegister extends CrudRepository<Session, Integer> {
}
