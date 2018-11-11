package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.attendee.Attendee;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code UnregisterCommand}.
 */


public class UnregisterCommandTest {
    private Model model = new ModelManager(getTypicalEventManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private String currUsername;

    @Before
    public void setUp() {
        User user = new UserBuilder().build();
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        model.logUser(user);
        currUsername = model.getUsername().toString();
    }

    @Test
    public void execute_registeredEventUnfilteredList_success() {
        Event eventToUnregister = model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased());

        // check current user is registered for event
        assertTrue(eventToUnregister.getAttendance().contains(new Attendee(currUsername)));
        UnregisterCommand unregisterCommand = new UnregisterCommand(INDEX_THIRD_EVENT);

        String expectedMessage =
                String.format(UnregisterCommand.MESSAGE_UNREGISTER_EVENT_SUCCESS, INDEX_THIRD_EVENT.getOneBased());

        Event unregisteredEvent = new EventBuilder(eventToUnregister)
                .withRemoveAttendees(currUsername).build();
        ModelManager expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToUnregister, unregisteredEvent);
        expectedModel.commitEventManager();

        assertCommandSuccess(unregisterCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unregisteredEventUnfilteredList_failure() {
        Event eventToUnregister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        // check current user is not registered for event
        assertFalse(eventToUnregister.getAttendance().contains(new Attendee(currUsername)));
        UnregisterCommand unregisterCommand = new UnregisterCommand(INDEX_FIRST_EVENT);

        Event unregisteredEvent = new EventBuilder(eventToUnregister).withRemoveAttendees(currUsername).build();
        model.updateEvent(eventToUnregister, unregisteredEvent);
        model.commitEventManager();

        assertCommandFailure(unregisterCommand, model, commandHistory, UnregisterCommand.MESSAGE_NOT_REGISTERED);
    }

    /**
     * 1. Unregisters for a {@code Event} from a filtered list.
     * 2. Undo the unregistration.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously unregistered event in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the unregistration. This ensures {@code RedoCommand} unregisters for the event object
     * regardless of indexing.
     */
    @Test
    public void executeUndoRedo_registeredEventFilteredList_sameEventUnregistered() throws Exception {
        Model expectedModel = new ModelManager(model.getEventManager(), new UserPrefs());
        UnregisterCommand unregisterCommand = new UnregisterCommand(INDEX_FIRST_EVENT);

        showEventAtIndex(model, INDEX_THIRD_EVENT);
        Event eventToUnregister = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        assertTrue(eventToUnregister.getAttendance().contains(new Attendee(currUsername)));

        Event unregisteredEvent = new EventBuilder(eventToUnregister).withRemoveAttendees(currUsername).build();
        expectedModel.logUser(new UserBuilder().build());
        expectedModel.updateEvent(eventToUnregister, unregisteredEvent);
        expectedModel.commitEventManager();

        // unregister -> unregisters from third event in unfiltered event list / first event in filtered event list
        unregisterCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered event list to show all persons
        expectedModel.undoEventManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(unregisteredEvent, model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased()));
        // redo -> unregisters from same third event in unfiltered event list
        expectedModel.redoEventManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        UnregisterCommand unregisterFirstCommand = new UnregisterCommand(INDEX_FIRST_EVENT);
        UnregisterCommand unregisterSecondCommand = new UnregisterCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(unregisterFirstCommand.equals(unregisterFirstCommand));

        // same values -> returns true
        UnregisterCommand unregisterFirstCommandCopy = new UnregisterCommand(INDEX_FIRST_EVENT);
        assertTrue(unregisterFirstCommand.equals(unregisterFirstCommandCopy));

        // different types -> returns false
        assertFalse(unregisterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unregisterFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(unregisterFirstCommand.equals(unregisterSecondCommand));
    }
}
