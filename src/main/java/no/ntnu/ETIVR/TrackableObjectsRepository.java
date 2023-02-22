package no.ntnu.ETIVR;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectsRepository extends CrudRepository<TrackableObjects, String> {
}
