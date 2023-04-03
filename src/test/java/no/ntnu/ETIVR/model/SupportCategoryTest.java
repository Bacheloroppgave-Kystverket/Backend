package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class SupportCategoryTest extends DefaultTest {

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();

    }

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();

    }

    @Test
    @DisplayName("Tests constructor with valid input")
    void testConstructorValidInput() {
        String categoryName = "Fixations";
        String introduction = "introduction to fixations";
        List<SupportItem> supportItems = new ArrayList<>();
        SupportCategory supportCategory;
        try {
            supportCategory = new SupportCategory(0, categoryName, introduction, supportItems);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Excpected the support category", "to be made", e);
        }
    }


    @Test
    @DisplayName("Tests constructor with invalid input")
    void testConstructorInvalidInput() {
        String categoryName = "Fixations";
        String introduction = "introduction to fixations";
        List<SupportItem> supportItems = new ArrayList<>();
        SupportCategory supportCategory;
        try {
            supportCategory = new SupportCategory(-1, categoryName, introduction, supportItems);
            addError(getIllegalPrefix(), "the id of support category cannot be negative");
        } catch (IllegalArgumentException e) {}
        try {
            supportCategory = new SupportCategory(-1, "", introduction, supportItems);
            addError(getIllegalPrefix(), "the name of support category cannot be empty");
        } catch (IllegalArgumentException e) {}try {
            supportCategory = new SupportCategory(-1, categoryName, "", supportItems);
            addError(getIllegalPrefix(), "the introduction of support category cannot be negative");
        } catch (IllegalArgumentException e) {}try {
            supportCategory = new SupportCategory(-1, categoryName, introduction, null);
            addError(getIllegalPrefix(), "the list of support categories cannot be null");
        } catch (IllegalArgumentException e) {}

    }
}
