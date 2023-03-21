package no.ntnu.ETIVR.model;

import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface Log<T> {

  /**
   * Gets the log.
   * @return the log items.
   */
  List<T> getLog();
}
