package no.ntnu.ETIVR.model;

public class SupportItem {
    private String supportItemName;
    private String explanation;

    public SupportItem(String supportItemName, String explanation) {
        this.supportItemName = supportItemName;
        this.explanation = explanation;
    }

    public String getSupportItemName() {
        return supportItemName;
    }

    public void setSupportItemName(String supportItemName) {
        this.supportItemName = supportItemName;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
