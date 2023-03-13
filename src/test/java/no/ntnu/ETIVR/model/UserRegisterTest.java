package no.ntnu.ETIVR.model;
import no.ntnu.ETIVR.Main;
import no.ntnu.ETIVR.model.exceptions.CouldNotAddUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotGetUserException;
import no.ntnu.ETIVR.model.exceptions.CouldNotRemoveUserException;
import no.ntnu.ETIVR.model.registers.UserRegister;
import no.ntnu.ETIVR.model.services.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests the trackable objects register.
 */
@SpringBootTest(classes = Main.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRegisterTest extends DefaultTest implements RegisterTest {

    private UserRegister userRegister;

    private User userInRegister;

    private String removeException;

    private String addException;

    private String getException;


    /**
     * Makes an instance of the user register test and tests the class.
     *
     * @param userService
     */

    @Autowired
    public UserRegisterTest(UserService userService) {
        super();
        this.userRegister = userService;
        checkIfUserIsNull(userService, "User register");
        removeException = "makesExceptionString()";
    }

    @Override
    @BeforeEach
    public void SetupTestData() {
        setUpStringBuilder();
        try {
            for (User user : userRegister.getAllUsers()) {
                userRegister.removeUserWithId(user.getUserId());
            }
            userRegister.addNewUser(makeDefaultUser());
            userInRegister = userRegister.getAllUsers().get(0);
        } catch (IllegalArgumentException | CouldNotRemoveUserException | CouldNotAddUserException exception) {
            fail(makeCouldNotGetDefaultString("test data"));
            exception.printStackTrace();
        }
    }

    @Override
    @AfterAll
    public void cleanUp() {
        try {
            List<User> users = userRegister.getAllUsers();
            for (User user : users) {
                userRegister.removeUserWithId(user.getUserId());
            }

        } catch (CouldNotRemoveUserException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }

    }

    @Override
    @AfterAll
    public void checkForErrors() {
        checkIfTestsFailedAndDisplayResult();
    }

    /**
     * Checks if a user is null.
     *
     * @param user  the objecct you want to check
     * @param error the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfUserIsNull(UserService user, String error) {
        if (user == null) {
            throw new IllegalArgumentException("The " + error + " can not be null.");
        }

    }

    /**
     * Makes a default user.
     *
     * @return the default user.
     */
    private User makeDefaultUser() {
        return new User(1l, "John", "1t3b");
    }

    /**
     * Tests if add user works with valid input
     */
    @Test
    @DisplayName("Tests if add user works with valid input")
    public void testIfAddUserWorksWithValidInput() {
        try {
            userRegister.addNewUser(null);
            addError(getIllegalPrefix(), "the input is null");
        } catch (IllegalArgumentException exception) {

        } catch (CouldNotAddUserException exception) {
            addErrorWithException(addException, "input is null", exception);

        }

        try {
            userRegister.addNewUser(userInRegister);
        } catch (IllegalArgumentException exception) {
            addErrorWithException(addException, "Input is already in the register", exception);
        } catch (CouldNotAddUserException exception) {

        }

    }


    /**
     * Test if remove user with ID works with valid input.
     */
    @Test
    @DisplayName("Tests if remove trackble object with ID works with valid input")
    public void testIfRemoveUserWithIdWorksWithValidInput() {
        try {
            userRegister.removeUserWithId(userInRegister.getUserId());
        } catch (IllegalArgumentException | CouldNotRemoveUserException exception) {
            addErrorWithException("Expected the object to be removed", "since its in the register", exception);
        }
    }

    /**
     * Tests if remove user with ID works with invalid input.
     */
    @Test
    @DisplayName("Tests if remove user with ID works with invalid input.")
    public void testIfRemoveUserWithIDWorksWithInvalidInput() {
        try {
            userRegister.removeUserWithId(-2);
            addError(getIllegalPrefix(), "the input is negative");
        } catch (IllegalArgumentException exception) {

        } catch (CouldNotRemoveUserException exception) {
            addErrorWithException(getIllegalPrefix(), "the input is negative", exception);
        }

        try {
            userRegister.removeUserWithId(60000);
            addError(removeException, "the input ID is not in the register");
        } catch (CouldNotRemoveUserException exception) {
        }
    }

    /**
     * Tests if get user with id works with valid input.
     */
    @Test
    @DisplayName("Tests if get user with id works with valid input.")
    public void testIfFindUserByIdWorksWithValidInput(){
        try{
            userRegister.findUserById(userInRegister.getUserId());
        }catch ( CouldNotGetUserException | IllegalArgumentException exception){
            addErrorWithException("Expected the user to be recived since","the input is valid", exception);
        }
    }

    /**
     * Tests if find user works with invalid input.
     */
    @Test
    @DisplayName("Tests if Find user works with invalid input.")
    public void testIfFindUserWorksWithInvalidInput(){
        try{
            userRegister.findUserById(-2);
            addError(getIllegalPrefix(),"the input is negative");
        }catch (IllegalArgumentException exception){

        }catch (CouldNotGetUserException exception) {
            addErrorWithException(getIllegalPrefix(), "the input is negative", exception);
        }

        try{
            userRegister.findUserById(5000);
            addError(getException,"the input is not in the register");
            } catch (IllegalArgumentException exception){
            addErrorWithException(getException, "the input is not in the register", exception);
        } catch (CouldNotGetUserException exception){
            
        }
    }
}
