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

    /**
     * Tests constructor with valid input
     */
    @Test
    @DisplayName("Tests constructor with valid input")
    void testConstructorValidInput() {
        String categoryName = "Fixations";
        String introduction = "introduction to fixations";
        List<SupportItem> supportItems = new ArrayList<>();
        int iconNumber = 1;
        SupportCategory supportCategory;
        try {
            supportCategory = new SupportCategory(0, categoryName, introduction, supportItems, iconNumber);
        } catch (IllegalArgumentException e) {
            addErrorWithException("Excpected the support category", "to be made", e);
        }
    }


    /**
     * Tests constructor with invalid input
     */
    @Test
    @DisplayName("Tests constructor with invalid input")
    void testConstructorInvalidInput() {
        String categoryName = "Fixations";
        String introduction = "introduction to fixations";
        List<SupportItem> supportItems = new ArrayList<>();
        SupportCategory supportCategory;
        int iconNumber = 1;
        try {
            supportCategory = new SupportCategory(-1, categoryName, introduction, supportItems, iconNumber);
            addError(getIllegalPrefix(), "the id of support category cannot be negative");
        } catch (IllegalArgumentException e) {}
        try {
            supportCategory = new SupportCategory(-1, "", introduction, supportItems, iconNumber);
            addError(getIllegalPrefix(), "the name of support category cannot be empty");
        } catch (IllegalArgumentException e) {}try {
            supportCategory = new SupportCategory(-1, categoryName, "", supportItems, iconNumber);
            addError(getIllegalPrefix(), "the introduction of support category cannot be negative");
        } catch (IllegalArgumentException e) {}try {
            supportCategory = new SupportCategory(-1, categoryName, introduction, null, iconNumber);
            addError(getIllegalPrefix(), "the list of support categories cannot be null");
        } catch (IllegalArgumentException e) {}
        try {
            supportCategory = new SupportCategory(-1, categoryName, introduction, null, -2);
            addError(getIllegalPrefix(), "the icon number is negative");
        }catch (IllegalArgumentException exception){}
    }
}
