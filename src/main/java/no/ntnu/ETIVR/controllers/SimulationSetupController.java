package no.ntnu.ETIVR.controllers;

import java.util.List;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@CrossOrigin
@RequestMapping("/simulationSetup")
public class SimulationSetupController {

  private SimulationSetupRegister simulationSetupRegister;

  /**
   * Makes an instance of the SimulationSetupController class.
   *
   * @param simulationSetupService the simulation setup service.
   */
  public SimulationSetupController(SimulationSetupService simulationSetupService) {
    checkIfObjectIsNull(simulationSetupService, "simulation setup service");
    this.simulationSetupRegister = simulationSetupService;
  }

  /**
   * Gets all the simulation setups.
   * @return the list of simulation setups.
   */
  @GetMapping
  public List<SimulationSetup> getAllSimulationSetups() {
    return simulationSetupRegister.getSimulationSetups();
  }

  /**
   * Checks if an object is null.
   *
   * @param object the object you want to check.
   * @param error  the error message the exception should have.
   * @throws IllegalArgumentException gets thrown if the object is null.
   */
  private void checkIfObjectIsNull(Object object, String error) {
    if (object == null) {
      throw new IllegalArgumentException("The " + error + " cannot be null.");
    }
  }
}
