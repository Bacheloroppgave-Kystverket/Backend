package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.trackable.TrackableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for trackable object
 */
@Repository
public interface TrackableObjectRepository extends JpaRepository<TrackableObject, Long> {
}
