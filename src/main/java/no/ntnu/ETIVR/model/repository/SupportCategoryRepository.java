package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.SupportCategory;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportCategoryRepository extends JpaRepository<SupportCategory, Long> {
}
