package no.ntnu.ETIVR.model.services;

import no.ntnu.ETIVR.model.SimulationSetup;
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
    public void addSimulationSetup(SimulationSetup simulationSetup) throws CouldNotAddSimulationSetupException {
        checkIfObjectIsNull(simulationSetup, "simulation setup");
        if(!simulationSetupRepository.existsById(simulationSetup.getSimulationSetupId())){
            simulationSetupRepository.save(simulationSetup);
        }else{
            throw new CouldNotAddSimulationSetupException("There is already a session with " + simulationSetup.getSimulationSetupId() + " as id in the register.");
        }
    }

    @Override
    public SimulationSetup getSimulationSetupById(long simulationSetupId) throws CouldNotGetSimulationSetupException {
        checkFloat(simulationSetupId, "simulation setup");
        Optional<SimulationSetup> setupOptinal = simulationSetupRepository.findById(simulationSetupId);
        if(setupOptinal.isEmpty()){
            throw new CouldNotGetSimulationSetupException("The simulation setup with id " + simulationSetupId + " is not in the register.");
        }
        return setupOptinal.get();
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
