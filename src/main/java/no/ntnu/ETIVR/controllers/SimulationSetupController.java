package no.ntnu.ETIVR.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddTrackableObjectException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSimulationSetupException;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.registers.TrackableObjectRegister;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@RequestMapping("/simulationSetup")
@CrossOrigin
public class SimulationSetupController {

  private SimulationSetupRegister simulationSetupRegister;

  private TrackableObjectRegister trackableObjectRegister;

  /**
   * Makes an instance of the SimulationSetupController class.
   *
   * @param simulationSetupService the simulation setup service.
   * @param trackableObjectsService the trackable objects service.
   */
  public SimulationSetupController(SimulationSetupService simulationSetupService, TrackableObjectsService trackableObjectsService) {
    checkIfObjectIsNull(simulationSetupService, "simulation setup service");
    checkIfObjectIsNull(trackableObjectsService, "trackable objects service");
    this.simulationSetupRegister = simulationSetupService;
    this.trackableObjectRegister = trackableObjectsService;
  }

  /**
   * Gets all the simulation setups.
   * @return the list of simulation setups.
   */
  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public List<SimulationSetup> getAllSimulationSetups() {
    return simulationSetupRegister.getSimulationSetups();
  }

  /**
   * Get simulation setup name
   * @param setupName String
   * @return simulation setup name
   * @throws CouldNotGetSimulationSetupException gets thrown if simulation setup is not found
   */
  @GetMapping("/{setupName}")
  @PreAuthorize("hasRole('USER')")
  public SimulationSetup getSimulationSetupByName(@PathVariable("setupName") String setupName)
      throws CouldNotGetSimulationSetupException {
    return simulationSetupRegister.getSimulationSetupByName(setupName);
  }

  /**
   * Adds simulation setup
   * @param body String
   * @throws JsonProcessingException gets thrown if JSON could not be processed
   * @throws CouldNotAddSimulationSetupException gets thrown if simulation could not be added
   * @throws CouldNotAddTrackableObjectException gets thrown if trackable object could not be added
   */
  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public void addSimulationSetup(@RequestBody String body)
      throws JsonProcessingException, CouldNotAddSimulationSetupException,
      CouldNotAddTrackableObjectException {
    SimulationSetup simulationSetup = makeSimulationSetup(body);
    addTrackableObjects(simulationSetup.getCloseTrackableObjects());
    simulationSetupRegister.addSimulationSetup(simulationSetup);
  }

  /**
   * Adds the trackable objects.
   * @param trackableObjects the trackable objects.
   * @throws CouldNotAddTrackableObjectException gets thrown if the trackable object is already in the register.
   */
  private void addTrackableObjects(List<TrackableObject> trackableObjects)
      throws CouldNotAddTrackableObjectException {
    for(TrackableObject trackableObject : trackableObjects){
      this.trackableObjectRegister.addTrackableObject(trackableObject);
    }
  }

  /**
   * Makes a simulation setup from a json string.
   * @param body the json string.
   * @return the simulation setup.
   * @throws JsonProcessingException gets thrown if the format is invalid.
   */
  private SimulationSetup makeSimulationSetup(String body) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(body, SimulationSetup.class);
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
