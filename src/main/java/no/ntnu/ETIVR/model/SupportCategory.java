package no.ntnu.ETIVR.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class SupportCategory {

    @Id
    private long supportCategoryId;
    private int iconNumber;
    private String categoryName;
    private String introduction;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "supportItems", joinColumns = @JoinColumn(name = "supportCategoryId"))
    private List<SupportItem> listOfItems;

    public SupportCategory() {
    }

    public SupportCategory(long supportCategoryId, String categoryName, String introduction, List<SupportItem> listOfItems) {
        this.supportCategoryId = supportCategoryId;
        this.categoryName = categoryName;
        this.introduction = introduction;
        this.listOfItems = listOfItems;
    }

    public long getSupportCategoryId() {
        return supportCategoryId;
    }

    public void setSupportCategoryId(long supportCategoryId) {
        this.supportCategoryId = supportCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<SupportItem> getListOfItems() {
        return listOfItems;
    }

    public void setListOfItems(List<SupportItem> listOfItems) {
        this.listOfItems = listOfItems;
    }
}
