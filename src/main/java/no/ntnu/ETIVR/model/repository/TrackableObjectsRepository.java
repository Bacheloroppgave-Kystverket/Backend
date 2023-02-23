package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.TrackableObjects;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectsRepository extends CrudRepository<TrackableObjects, String> {
}
