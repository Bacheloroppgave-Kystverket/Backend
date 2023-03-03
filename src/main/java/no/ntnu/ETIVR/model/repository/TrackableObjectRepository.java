package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.TrackableObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectRepository extends CrudRepository<TrackableObject, Long> {
}
