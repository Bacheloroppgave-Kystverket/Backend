package no.ntnu.ETIVR.model.repository;

import java.util.Optional;
import no.ntnu.ETIVR.model.SimulationSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for simulation setup
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface SimulationSetupRepository extends JpaRepository<SimulationSetup, Long> {

  @Query(value = "SELECT * FROM simulationSetup WHERE nameOfSetup = :nameOfSetup", nativeQuery = true)
  Optional<SimulationSetup> findBySetupName(@Param("nameOfSetup") String nameOfSetup);
}
