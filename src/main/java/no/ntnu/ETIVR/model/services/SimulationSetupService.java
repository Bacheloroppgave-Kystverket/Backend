package no.ntnu.ETIVR.model.services;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.ETIVR.model.SimulationTemplate;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSimulationSetupException;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.repository.SimulationSetupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class SimulationSetupService implements SimulationSetupRegister {

    private SimulationSetupRepository simulationSetupRepository;

    /**
     * Makes an instance of the SimulationSetupService class.
     * @param simulationSetupRepository the simulation setup repository
     */
    public SimulationSetupService(SimulationSetupRepository simulationSetupRepository) {
        this.simulationSetupRepository = simulationSetupRepository;

    }

    @Override
    public void addSimulationSetup(SimulationTemplate simulationTemplate) throws CouldNotAddSimulationSetupException {
        checkIfObjectIsNull(simulationTemplate, "simulation setup");
        if(!simulationSetupRepository.existsById(simulationTemplate.getSimulationSetupId())){
            simulationSetupRepository.save(simulationTemplate);
        }else{
            throw new CouldNotAddSimulationSetupException("There is already a session with " + simulationTemplate.getSimulationSetupId() + " as id in the register.");
        }
    }

    @Override
    public SimulationTemplate getSimulationSetupById(long simulationSetupId) throws CouldNotGetSimulationSetupException {
        checkFloat(simulationSetupId, "simulation setup");
        Optional<SimulationTemplate> setupOptinal = simulationSetupRepository.findById(simulationSetupId);
        if(setupOptinal.isEmpty()){
            throw new CouldNotGetSimulationSetupException("The simulation setup with id " + simulationSetupId + " is not in the register.");
        }
        return setupOptinal.get();
    }

    @Override
    public List<SimulationTemplate> getSimulationSetups() {
        List<SimulationTemplate> simulationTemplates = new ArrayList<>();
        simulationSetupRepository.findAll().forEach(simulationTemplates::add);
        return simulationTemplates;
    }

    /**
     * Checks if a float is above zero.
     * @param numberToCheck the number to check.
     * @param error the error.
     */
    public void checkFloat(float numberToCheck, String error){
        if(numberToCheck < 0){
            throw new IllegalArgumentException("The " + error + " cannot be below zero");
        }
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
