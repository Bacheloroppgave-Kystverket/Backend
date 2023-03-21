package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.trackable.TrackableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackableObjectRepository extends JpaRepository<TrackableObject, Long> {
}
