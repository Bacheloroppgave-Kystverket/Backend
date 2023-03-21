package no.ntnu.ETIVR.model.repository;

import no.ntnu.ETIVR.model.SimulationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface SimulationSetupRepository extends JpaRepository<SimulationTemplate, Long> {

}
