package no.ntnu.ETIVR.model.repository;

import java.util.Optional;
import no.ntnu.ETIVR.model.SimulationSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for simulation setup
 */
public interface SimulationSetupRepository extends JpaRepository<SimulationSetup, Long> {

  /**
   * Gets a simulation setup based on its name.
   * @param nameOfSetup the name of the setup.
   * @return the setup matching that name.
   */
  @Query(value = "SELECT * FROM simulationSetup WHERE nameOfSetup = :nameOfSetup", nativeQuery = true)
  Optional<SimulationSetup> findBySetupName(@Param("nameOfSetup") String nameOfSetup);
}
