package no.ntnu.ETIVR.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest extends DefaultTest{

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
     * Tests if the constructor works with valid input
     */
    @Test
    @DisplayName("Tests if the constructor works with valid input")
    void testConstructorValidInput(){
        try {
            User user = new User(2l, "John","1t3b");
        }catch (IllegalArgumentException e){
            addErrorWithException("Expected the user", "to be made", e);
        }
    }

    /**
     * Tests if the constructor works with invalid input
     */
    @Test
    @DisplayName("Test if the constructor works with invalid input")
    void testConstructorInvalidInput(){
        User user;
        try {
            user = new User(-2l,"John","1t3b");
            addError(getIllegalPrefix(),"the user id can not be under 0");
        }catch (IllegalArgumentException e){
        }
        try {
            user = new User(2l, null,"1t3b");
            addError(getIllegalPrefix(),"the username can not be null");
        }catch (IllegalArgumentException e){

        }
        try {
            user = new User(2l,"John",null);
            addError(getIllegalPrefix(),"password can not be null");
        } catch (IllegalArgumentException E){

        }
    }
}
