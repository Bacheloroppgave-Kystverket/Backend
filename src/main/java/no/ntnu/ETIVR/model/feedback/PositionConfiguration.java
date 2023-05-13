package no.ntnu.ETIVR.model.feedback;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Entity
public class PositionConfiguration {

  @Id
  @GeneratedValue
  private long positionConfigId;

  @ElementCollection(fetch = FetchType.EAGER, targetClass = CategoryConfiguration.class)
  @CollectionTable(name = "positionConfigurations", joinColumns = @JoinColumn(name = "positionConfigId"))
  private List<CategoryConfiguration> categoryConfigurations;

  /**
   * Makes an instance of the PositionConfiguration class.
   */
  public PositionConfiguration() {

  }

  /**
   * Makes an instance of the PositionConfiguration.
   * @param categoryConfigurations the category configurations.
   */
  public PositionConfiguration(@JsonProperty("categoryConfigurations") List<CategoryConfiguration> categoryConfigurations) {
    checkIfObjectIsNull(categoryConfigurations, "feedback configurations");
    this.categoryConfigurations = categoryConfigurations;
  }

  /**
   * Checks if an object is null.
   * @param object the object you want to check.
   * @param error  the error message the exception should have.
   */
  private void checkIfObjectIsNull(Object object, String error) {
    if (object == null) {
      throw new IllegalArgumentException("The " + error + " cannot be null.");
    }
  }
}
