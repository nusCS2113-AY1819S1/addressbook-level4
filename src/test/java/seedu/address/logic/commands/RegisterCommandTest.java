package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code RegisterCommand}.
 */


public class RegisterCommandTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_UnregisteredEventUnfilteredList_success() {
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);

        String expectedMessage =
                String.format(RegisterCommand.MESSAGE_REGISTER_EVENT_SUCCESS,INDEX_FIRST_EVENT.getOneBased());

        // build new event with added attendee using current user username
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();

        ModelManager expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.updateEvent(eventToRegister, registeredEvent);
        expectedModel.commitEventManager();

        assertCommandSuccess(registerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_RegisteredEventUnfilteredList_failure() {
        Event eventToRegister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        RegisterCommand registerCommand = new RegisterCommand(INDEX_FIRST_EVENT);

        // add attendee using current user username to event, regardless of whether already registered
        String currUsername = model.getUsername().toString();
        Event registeredEvent = new EventBuilder(eventToRegister).withAddAttendees(currUsername).build();
        model.updateEvent(eventToRegister, registeredEvent);
        model.commitEventManager();

        assertCommandFailure(registerCommand, model, commandHistory, RegisterCommand.MESSAGE_ALREADY_REGISTERED);
    }

    @Test
    public void equals() {
        RegisterCommand registerFirstCommand = new RegisterCommand(INDEX_FIRST_EVENT);
        RegisterCommand registerSecondCommand = new RegisterCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(registerFirstCommand.equals(registerFirstCommand));

        // same values -> returns true
        RegisterCommand registerFirstCommandCopy = new RegisterCommand(INDEX_FIRST_EVENT);
        assertTrue(registerFirstCommand.equals(registerFirstCommandCopy));

        // different types -> returns false
        assertFalse(registerFirstCommand.equals(1));

        // null -> returns false
        assertFalse(registerFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(registerFirstCommand.equals(registerSecondCommand));
    }
}
