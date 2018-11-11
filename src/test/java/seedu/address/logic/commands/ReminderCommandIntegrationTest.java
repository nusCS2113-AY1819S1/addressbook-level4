package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

//@@author junweiljw
/**
 * Contains integration tests (interaction with the Model) for {@code ReminderCommand}.
 */
public class ReminderCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newReminder_success() {
        Reminder validReminder = new ReminderBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addReminder(validReminder);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ReminderCommand(validReminder), model, commandHistory,
                String.format(ReminderCommand.MESSAGE_SUCCESS, validReminder), expectedModel);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        Reminder reminderInList = model.getAddressBook().getReminderList().get(0);
        assertCommandFailure(new ReminderCommand(reminderInList), model, commandHistory,
                ReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

}
