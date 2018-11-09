package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
    }

    @Test
    public void equals() {

        String firstInput = "manager";
        String secondInput = "employee";
        String thirdInput = "alice@example.com";

        LoginCommand loginFirstCommand = new LoginCommand(firstInput, 2);
        LoginCommand loginSecondCommand = new LoginCommand(secondInput, 3);
        LoginCommand loginThirdCommand = new LoginCommand(thirdInput, 1);



        // same object -> returns true
        assertTrue(loginFirstCommand.equals(loginFirstCommand));

        // different types -> returns false
        assertFalse(loginFirstCommand.equals(1));

        // null -> returns false
        assertFalse(loginFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(loginFirstCommand.equals(loginSecondCommand));

        // same object -> returns true
        assertTrue(loginSecondCommand.equals(loginSecondCommand));

        // different types -> returns false
        assertFalse(loginSecondCommand.equals(1));

        // null -> returns false
        assertFalse(loginSecondCommand.equals(null));

        // same object -> returns true
        assertTrue(loginThirdCommand.equals(loginThirdCommand));

        // different types -> returns false
        assertFalse(loginThirdCommand.equals(1));

        // null -> returns false
        assertFalse(loginThirdCommand.equals(null));

        // same values -> returns true
        LoginCommand loginFirstCommandCopy = new LoginCommand(firstInput, 2);
        assertEquals(loginFirstCommand, loginFirstCommandCopy);

        // same values -> returns true
        LoginCommand loginSecondCommandCopy = new LoginCommand(secondInput, 3);
        assertEquals(loginSecondCommand, loginSecondCommandCopy);

        // same values -> returns true
        LoginCommand loginThirdCommandCopy = new LoginCommand(thirdInput, 1);
        assertEquals(loginThirdCommand, loginThirdCommandCopy);
    }
}
