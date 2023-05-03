package no.ntnu.ETIVR.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class SupportCategory {

    @Id
    @GeneratedValue
    private long supportCategoryId;
    private int iconNumber;
    private String categoryName;
    private String introduction;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "supportItems", joinColumns = @JoinColumn(name = "supportCategoryId"))
    private List<SupportItem> listOfItems;

    public SupportCategory() {
    }

    public SupportCategory(long supportCategoryId, String categoryName, String introduction, List<SupportItem> listOfItems, int iconNumber) {
        checkIfNumberNotNegative(supportCategoryId, "support category id");
        this.supportCategoryId = supportCategoryId;
        checkIfObjectIsNull(categoryName, "category name");
        this.categoryName = categoryName;
        checkIfObjectIsNull(introduction, "introduction");
        this.introduction = introduction;
        checkIfObjectIsNull(listOfItems, "list of items");
        this.listOfItems = listOfItems;
        checkIfNumberNotNegative(iconNumber, "icon number");
        this.iconNumber = iconNumber;
    }

    /**
     * Gets the icon number.
     * @return the icon number.
     */
    public int getIconNumber(){
        return iconNumber;
    }

    /**
     * Gets support category id
     * @return support category id
     */
    public long getSupportCategoryId() {
        return supportCategoryId;
    }

    /**
     * Set support category id
     * @param supportCategoryId support category id
     */
    public void setSupportCategoryId(long supportCategoryId) {
        this.supportCategoryId = supportCategoryId;
    }

    /**
     * Get category name
     * @return category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Set category name
     * @param categoryName category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets introduction
     * @return introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Set introduction
     * @param introduction String
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * Get list of items
     * @return list of items
     */
    public List<SupportItem> getListOfItems() {
        return listOfItems;
    }

    /**
     * Set list of items
     * @param listOfItems list of items to set
     */
    public void setListOfItems(List<SupportItem> listOfItems) {
        this.listOfItems = listOfItems;
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

    /**
     * Check to make sure that integer values cannot be negative.
     *
     * @param object the object to be checked.
     * @param error  exception message to be displayed.
     */
    private void checkIfNumberNotNegative(long object, String error) {
        if (object < 0) {
            throw new IllegalArgumentException("The " + error + " Cannot be negative values.");
        }
    }

}
