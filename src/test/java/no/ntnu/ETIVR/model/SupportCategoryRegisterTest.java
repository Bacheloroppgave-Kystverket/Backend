package no.ntnu.ETIVR.model;


import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveSupportCategoryException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveTrackableObjectException;
import no.ntnu.ETIVR.model.registers.SupportCategoryRegister;
import no.ntnu.ETIVR.model.services.SupportCategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the support category register class.
 */
@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SupportCategoryRegisterTest extends DefaultTest implements RegisterTest {

    private SupportCategoryRegister supportCategoryRegister;

    private SupportCategory supportCategory;

    private String removeException;

    private String addException;

    private String getException;

    @Autowired
    public SupportCategoryRegisterTest(SupportCategoryService supportCategoryService) {
        super();
        this.supportCategoryRegister = supportCategoryService;
        removeException = makeExceptionString(CouldNotRemoveTrackableObjectException.class.getSimpleName());
        addException = makeExceptionString(CouldNotAddSupportCategoryException.class.getSimpleName());
        getException = makeExceptionString(CouldNotGetSupportCategoryException.class.getSimpleName());
    }

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
        try {
            for (SupportCategory supportCategory : supportCategoryRegister.getSupportCategories()) {
                supportCategoryRegister.removeSupportCategory(supportCategory);
            }
            supportCategoryRegister.addSupportCategory(makeDefaultSupportCategory(2));
            this.supportCategory = supportCategoryRegister.getSupportCategories().get(0);
        } catch (CouldNotRemoveSupportCategoryException | CouldNotAddSupportCategoryException e) {
            fail(makeCouldNotGetDefaultString("support category"));
            e.printStackTrace();
        }
    }

    @Override
    @AfterEach
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    @Override
    @AfterAll
    public void cleanUp() {
        try {
            List<SupportCategory> supportCategories = this.supportCategoryRegister.getSupportCategories();
            for (SupportCategory supportCategory : supportCategories) {
                this.supportCategoryRegister.removeSupportCategory(supportCategory);
            }
        } catch (IllegalArgumentException | CouldNotRemoveSupportCategoryException e) {
            e.printStackTrace();
        }

    }

    /**
     * Tests if add support category works with invalid input
     */
    @Test
    @DisplayName("Tests if add support category works with invalid input")
    public void testIfAddSupportCategoryWorksWithInvalidInput() {
        try {
            this.supportCategoryRegister.addSupportCategory(null);
            addError(getIllegalPrefix(), "the input support category is null");
        } catch (IllegalArgumentException e) {

        } catch (CouldNotAddSupportCategoryException e) {
            addErrorWithException(getIllegalPrefix(), "the input support category is null", e);
        }
        try {
            this.supportCategoryRegister.addSupportCategory(supportCategory);
            addError(addException, "the input support category is null");
        } catch (IllegalArgumentException e) {
            addErrorWithException(addException, "the input support category is already in the register", e);
        } catch (CouldNotAddSupportCategoryException e) {
        }

    }

    /**
     * Tests if add support category works with valid input
     */
    @Test
    @DisplayName("Tests if addSupportCategory works with valid input")
    public void testIfAddSupportCategoryWorksWithValidInput() {
        try {
            this.supportCategoryRegister.addSupportCategory(makeDefaultSupportCategory(0));
        } catch (IllegalArgumentException | CouldNotAddSupportCategoryException e) {
            addErrorWithException("Expected the support category to be added since", "the input is valid", e);
        }
    }

    /**
     * Tests if remove support category works with invalid input
     */
    @Test
    @DisplayName("Tests is removeSupportCategory works with invalid input")
    public void testIfRemoveSupportCategoryWorksWithInvalidInput() {
        try {
            this.supportCategoryRegister.removeSupportCategory(null);
            addError(getIllegalPrefix(), "the input is null");
        } catch (IllegalArgumentException e) {
        } catch (CouldNotRemoveSupportCategoryException e) {
            addErrorWithException(getIllegalPrefix(), "the input is null", e);
        }
        try {
            this.supportCategoryRegister.removeSupportCategory(makeDefaultSupportCategory(0));
            addError(removeException, "the input support category is not in the register");
        } catch (IllegalArgumentException e) {
            addErrorWithException(removeException, "the input support category is not in the register", e);
        } catch (CouldNotRemoveSupportCategoryException e) {
        }
    }

    /**
     * Tests if remove support category works with valid input
     */
    @Test
    @DisplayName("Tests if removeSupportCategory works with valid input")
    public void testIfRemoveSupportCategoryWorksWithValidInput() {
        try {
            this.supportCategoryRegister.removeSupportCategory(supportCategory);
        } catch (IllegalArgumentException | CouldNotRemoveSupportCategoryException e) {
            addErrorWithException("Expected the support category to be removed since", "the input is valid", e);
        }
    }

    /**
     * Tests if get support category by id works with invalid input
     */
    @Test
    @DisplayName("Tests if getSupportCategoryById works with invalid input")
    public void testIfGetSupportCategoryByIdWorksWithInvalidInput() {
        try {
            supportCategoryRegister.getSupportCategoryById(0);
        } catch (IllegalArgumentException e) {
            addErrorWithException(getIllegalPrefix(), "the input id is empty", e);
        } catch (CouldNotGetSupportCategoryException e) {
        }
        try {
            supportCategoryRegister.getSupportCategoryById(-1);
        } catch (IllegalArgumentException e) {
        } catch (CouldNotGetSupportCategoryException e) {
            addErrorWithException(getIllegalPrefix(), "the input id is negative", e);
        }
        try {
            supportCategoryRegister.getSupportCategoryById(110000);
        } catch (IllegalArgumentException e) {
            addErrorWithException(getIllegalPrefix(), "the input support category is not in the register", e);
        } catch (CouldNotGetSupportCategoryException e) {
        }
    }

    /**
     * Tests if get support category by ID works with valid input
     */
    @Test
    @DisplayName("Tests if getSupportCategoryById works with valid input")
    public void testtIfGetSupportCategoryByIdWorksWithValidInput() {
        try {
            supportCategoryRegister.getSupportCategoryById(supportCategory.getSupportCategoryId());
        } catch (IllegalArgumentException | CouldNotGetSupportCategoryException e) {
            addErrorWithException("Expected the support category to be added since", "the input is valid", e);
        }
    }

    /**
     * Makes default support category
     * @param id long
     * @return list of support categories
     */
    private SupportCategory makeDefaultSupportCategory(long id) {
        List<SupportItem> supportItemList = new ArrayList<>();
        supportItemList.add(new SupportItem("Fixations", "explanation"));
        supportItemList.add(new SupportItem("Fixation duration", "explanation"));
        return new SupportCategory(id, "category name", "introduction", supportItemList, 1);
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
