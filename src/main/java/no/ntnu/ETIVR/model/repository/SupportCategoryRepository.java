package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.SupportCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for support category
 */
@Repository
public interface SupportCategoryRepository extends JpaRepository<SupportCategory, Long> {
}
