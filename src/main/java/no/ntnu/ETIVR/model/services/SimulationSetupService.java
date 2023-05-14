package no.ntnu.ETIVR.model.services;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSimulationSetupException;
import no.ntnu.ETIVR.model.registers.SimulationSetupRegister;
import no.ntnu.ETIVR.model.repository.SimulationSetupRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Represents the simulation setup service that handles JPA interaction.
 */
@Service
public class SimulationSetupService implements SimulationSetupRegister {

    private SimulationSetupRepository simulationSetupRepository;

    /**
     * Makes an instance of the SimulationSetupService class.
     * @param simulationSetupRepository the simulation setup repository
     */
    public SimulationSetupService(SimulationSetupRepository simulationSetupRepository) {
        checkIfObjectIsNull(simulationSetupRepository, "simulation setup repository");
        this.simulationSetupRepository = simulationSetupRepository;
    }

    @Override
    public void addSimulationSetup(SimulationSetup simulationSetup) throws CouldNotAddSimulationSetupException {
        checkSimulationSetup(simulationSetup);
        if (!simulationSetupRepository.existsById(simulationSetup.getSimulationSetupId())) {
            simulationSetupRepository.save(simulationSetup);
        } else {
            throw new CouldNotAddSimulationSetupException("There is already a session with " + simulationSetup.getSimulationSetupId() + " as id in the register.");
        }
    }

    @Override
    public void removeSimulationSetup(SimulationSetup simulationSetup) throws CouldNotRemoveSimulationSetupException {
        checkSimulationSetup(simulationSetup);
        if (simulationSetupRepository.existsById(simulationSetup.getSimulationSetupId())) {
            simulationSetupRepository.delete(simulationSetup);
        } else {
            throw new CouldNotRemoveSimulationSetupException("The simulation setup with id " + simulationSetup.getSimulationSetupId() + " is not in the register.");
        }
    }

    @Override
    public SimulationSetup getSimulationSetupById(long simulationSetupId) throws CouldNotGetSimulationSetupException {
        checkFloat(simulationSetupId, "simulation setup id");
        Optional<SimulationSetup> setupOptinal = simulationSetupRepository.findById(simulationSetupId);
        if (setupOptinal.isEmpty()) {
            throw new CouldNotGetSimulationSetupException("The simulation setup with id " + simulationSetupId + " is not in the register.");
        }
        return setupOptinal.get();
    }

    @Override
    public SimulationSetup getSimulationSetupByName(String setupName)
            throws CouldNotGetSimulationSetupException {
        checkString(setupName, "setup name");
        Optional<SimulationSetup> simulationSetup = simulationSetupRepository.findBySetupName(setupName);
        if (simulationSetup.isEmpty()) {
            throw new CouldNotGetSimulationSetupException("The simulation with that name is not in the register");
        }
        return simulationSetup.get();
    }

    @Override
    public List<SimulationSetup> getSimulationSetups() {
        List<SimulationSetup> simulationSetups = new ArrayList<>();
        simulationSetupRepository.findAll().forEach(simulationSetups::add);
        return simulationSetups;
    }

    /**
     * Checks if the simulation setup is not null.
     * @param simulationSetup the simulation setup
     */
    private void checkSimulationSetup(SimulationSetup simulationSetup) {
        checkIfObjectIsNull(simulationSetup, "simulation setup");
    }


    /**
     * Checks if a float is above zero.
     * @param numberToCheck the number to check.
     * @param error the error.
     */
    private void checkFloat(float numberToCheck, String error) {
        if (numberToCheck < 0) {
            throw new IllegalArgumentException("The " + error + " cannot be below zero");
        }
    }

    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
