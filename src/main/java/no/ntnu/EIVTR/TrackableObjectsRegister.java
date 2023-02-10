package no.ntnu.EIVTR;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectsRegister extends CrudRepository<TrackableObjects, Integer> {
}
