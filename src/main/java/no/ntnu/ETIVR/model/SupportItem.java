package no.ntnu.ETIVR.model;

import javax.persistence.Embeddable;

/**
 * Represents a sub-category of a support category.
 */
@Embeddable
public class SupportItem {
    private String supportItemName;
    private String explanation;

    /**
     * Makes an empty instance of support item
     */
    public SupportItem() {
    }

    /**
     * Makes an instance of support item
     * @param supportItemName String
     * @param explanation String
     */
    public SupportItem(String supportItemName, String explanation) {
        this.supportItemName = supportItemName;
        this.explanation = explanation;
    }

    /**
     * Gets support item name
     * @return support item name
     */
    public String getSupportItemName() {
        return supportItemName;
    }

    /**
     * Adds support item name
     * @param supportItemName String
     */
    public void setSupportItemName(String supportItemName) {
        this.supportItemName = supportItemName;
    }

    /**
     * Gets explanation
     * @return explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Sets explanation
     * @param explanation String
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
