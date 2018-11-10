//@@author cqinkai
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EventManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.UserBuilder;

/**
 * Tests for execution of {@code ReminderCommand}
 * with integration of {@code UndoCommand}, {@code RedoCommand} and {@code RegisterCommand}.
 * Does not work with {@code UndoCommand} and {@code RedoCommand}
 * but works only after {@code RegisterCommand} on an upcoming (24 hours time frame) {@code Event}.
 */
public class ReminderCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyEventManagerWithoutLogin_throwsCommandException() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());

        assertCommandFailure(new ReminderCommand(), model, commandHistory, ReminderCommand.MESSAGE_NOT_LOGGED_IN);
    }

    @Test
    public void execute_nonEmptyEventManagerWithoutLogin_throwsCommandException() {
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());

        assertCommandFailure(new ReminderCommand(), model, commandHistory, ReminderCommand.MESSAGE_NOT_LOGGED_IN);
    }

    @Test
    public void execute_emptyEventManagerWithLogin_returnsMessageNoUpcomingEvents() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());
        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);

        assertCommandSuccess(new ReminderCommand(), model, commandHistory, ReminderCommand.MESSAGE_NO_UPCOMING_EVENTS,
                expectedModel);

        // undo/redo stack does not record ReminderCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_nonEmptyEventManagerWithLogin_returnsMessageNoUpcomingEvents() {
        model = new ModelManager(getTypicalEventManager(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalEventManager(), new UserPrefs());
        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);

        assertCommandSuccess(new ReminderCommand(), model, commandHistory, ReminderCommand.MESSAGE_NO_UPCOMING_EVENTS,
                expectedModel);

        // undo/redo stack does not record ReminderCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_withUserRegistered_success() {
        model = new ModelManager(new EventManager(), new UserPrefs());
        expectedModel = new ModelManager(new EventManager(), new UserPrefs());
        User user = new UserBuilder().build();
        model.logUser(user);
        expectedModel.logUser(user);

        Event upcomingEvent = createUpcomingEvent(user.getUsername());
        model.addEvent(upcomingEvent);
        expectedModel.addEvent(upcomingEvent);

        assertCommandSuccess(new ReminderCommand(), model, commandHistory, ReminderCommand.MESSAGE_REMINDER_SENT,
                expectedModel);

        // undo/redo stack does not record ReminderCommand
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * Creates an upcoming event with user with the {@code username} already registered as an {@code Attendee}.
     * @param username
     */
    private Event createUpcomingEvent(Username username) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date currentDate = new Date();
        Long currentDateInMillis = currentDate.getTime();

        // get the date in 24 hours' time
        Long newDateInMillis = currentDateInMillis + TimeUnit.MILLISECONDS.convert((long) 24, TimeUnit.HOURS);
        String newDate = dateFormat.format(new Date(newDateInMillis));

        Event upcomingEvent = new EventBuilder().withDateTime(newDate).withAttendees(username.toString())
                .build();

        return upcomingEvent;
    }
}
