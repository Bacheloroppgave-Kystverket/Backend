package no.ntnu.ETIVR.model.registers;

import java.util.List;
import no.ntnu.ETIVR.model.SimulationSetup;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSimulationSetupException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSimulationSetupException;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface SimulationSetupRegister {

    /**
     * Adds a simulation setup to the register.
     * @param simulationSetup the simulation setup.
     * @throws CouldNotAddSimulationSetupException gets thrown if the simulation setup is already in the register.
     */
    void addSimulationSetup(SimulationSetup simulationSetup) throws CouldNotAddSimulationSetupException;

    /**
     * Gets a simulation setup by id.
     * @param simulationSetupId the id of the simulation setup.
     * @return the simulation setup.
     * @throws CouldNotGetSimulationSetupException gets thrown if there is no simulation setup with that id.
     */
    SimulationSetup getSimulationSetupById(long simulationSetupId) throws CouldNotGetSimulationSetupException;

    /**
     * Gets all the simulation setups in the register.
     * @return the simulation setups.
     */
    List<SimulationSetup> getSimulationSetups();

}
