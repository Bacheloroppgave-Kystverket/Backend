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
import no.ntnu.ETIVR.model.repository.SimulationSetupRepository;
import no.ntnu.ETIVR.model.repository.TrackableObjectRepository;
import no.ntnu.ETIVR.model.services.SimulationSetupService;
import no.ntnu.ETIVR.model.services.TrackableObjectsService;
import no.ntnu.ETIVR.model.trackable.TrackableObject;
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
@CrossOrigin
@RequestMapping("/simulationSetup")
public class SimulationSetupController {

  private SimulationSetupRegister simulationSetupRegister;

  private TrackableObjectRegister trackableObjectRegister;

  /**
   * Makes an instance of the SimulationSetupController class.
   *
   * @param simulationSetupRepository the simulation setup repository.
   * @param trackableObjectRepository the trackable object repository.
   */
  public SimulationSetupController(SimulationSetupRepository simulationSetupRepository, TrackableObjectRepository trackableObjectRepository) {
    checkIfObjectIsNull(simulationSetupRepository, "simulation setup repository");
    checkIfObjectIsNull(trackableObjectRepository, "trackable objects repository");
    this.simulationSetupRegister = new SimulationSetupService(simulationSetupRepository);
    this.trackableObjectRegister = new TrackableObjectsService(trackableObjectRepository);
  }

  /**
   * Gets all the simulation setups.
   * @return the list of simulation setups.
   */
  @GetMapping
  public List<SimulationSetup> getAllSimulationSetups() {
    return simulationSetupRegister.getSimulationSetups();
  }

  @GetMapping("/{setupId}")
  public SimulationSetup getSimulationSetupById(@PathVariable("setupId") long id)
      throws CouldNotGetSimulationSetupException {
    return simulationSetupRegister.getSimulationSetupById(id);
  }


  /**
   * Adds a simulation to the register.
   * @param body the json body.
   * @throws JsonProcessingException gets thrown if the format of the JSON file is invalid.
   * @throws CouldNotAddSimulationSetupException gets thrown if the simulation setup is already in the system.
   */
  @PostMapping
  public void addSimulationSetup(@RequestBody String body)
      throws JsonProcessingException, CouldNotAddSimulationSetupException,
      CouldNotAddTrackableObjectException {
    SimulationSetup simulationSetup = makeSimulationSetupFromJson(body);
    for (TrackableObject trackableObject : simulationSetup.getTrackableObjects()){
      trackableObjectRegister.addTrackableObject(trackableObject);
    }
    simulationSetupRegister.addSimulationSetup(simulationSetup);
  }

  /**
   * Makes a simulation setup from a JSON file.
   * @param body the JSON body
   * @return the simulation setup
   * @throws JsonProcessingException gets thrown if the JSON is invalid format
   */
  private SimulationSetup makeSimulationSetupFromJson(String body) throws JsonProcessingException
  {
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
