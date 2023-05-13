package no.ntnu.ETIVR.model.registers;

import no.ntnu.ETIVR.model.position.ReferencePosition;

/**
 * Represents reference position register
 */
public interface ReferencePositionRegister {

  /**
   * Adds reference position to list
   * @param referencePosition ReferencePosition
   */
  void addReferencePosition(ReferencePosition referencePosition);
}
