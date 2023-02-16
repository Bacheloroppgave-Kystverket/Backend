package no.ntnu.ETIVR;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectsRegister extends CrudRepository<TrackableObjects, String> {
}
