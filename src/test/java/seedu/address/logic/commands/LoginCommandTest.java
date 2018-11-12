package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;


/**
 * This is a test class for LoginCommandTest
 */
public class LoginCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_loginSuccessful() {

        /*StorageController.enterTestMode();
        UserManager.getInstance().addUser(new User("defaultUser", "password", 1));

        assertCommandSuccess(new LoginCommand(new User("defaultUser", "password", 1)), new CommandHistory(),
                MESSAGE_AUTH_SUCCESS);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);*/

    }
}
