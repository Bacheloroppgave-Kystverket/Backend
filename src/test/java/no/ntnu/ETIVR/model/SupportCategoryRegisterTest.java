package no.ntnu.ETIVR.model;


import no.ntnu.ETIVR.model.registers.SupportCategoryRegister;
import no.ntnu.ETIVR.model.services.SupportCategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SupportCategoryRegisterTest extends DefaultTest{

    private SupportCategoryRegister supportCategoryRegister;

    private SupportCategory supportCategoryInRegister;

    private String removeException;

    private String addException;

    private String getException;

    public SupportCategoryRegisterTest(SupportCategoryService supportCategoryService) {
        super();
        this.supportCategoryRegister = supportCategoryService;
        checkIfObjectIsNull(supportCategoryService, "support category service");
    }

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
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
