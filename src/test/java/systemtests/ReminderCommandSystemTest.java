package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalReminders.REMINDER1;
import static seedu.address.testutil.TypicalReminders.REMINDER2;
import static seedu.address.testutil.TypicalReminders.REMINDER_A;
import static seedu.address.testutil.TypicalReminders.KEYWORD_MATCHING_REMINDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.suggestions.WrongCommandSuggestion;
import seedu.address.model.Model;
import seedu.address.model.todo.Title;
//import seedu.address.model.person.Date;
import seedu.address.model.person.Time;
//import seedu.address.model.reminder.Title;
import seedu.address.model.reminder.Date;
//import seedu.address.model.reminder.Time;
import seedu.address.model.reminder.Agenda;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;
import seedu.address.testutil.ReminderUtil;

//@@author junweiljw
public class ReminderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        /* ------------------------ Perform reminder operations on the shown unfiltered list --------------------------- */

        /* Case: add a reminder
         * -> added
         */
        Reminder toAdd = REMINDER1;
        String command = "   " + ReminderCommand.COMMAND_WORD + "  " + TITLE_DESC_REMINDER1 + "  " + DATE_DESC_REMINDER1
                + " " + TIME_DESC_REMINDER1 + AGENDA_DESC_REMINDER1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with parameters in random order -> added */
        toAdd = REMINDER2;
        command = ReminderCommand.COMMAND_WORD + DATE_DESC_REMINDER2 + AGENDA_DESC_REMINDER2 + TITLE_DESC_REMINDER2
                + TIME_DESC_REMINDER2;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the address book except title -> added */
        toAdd = new ReminderBuilder(REMINDER1).withTitle(VALID_REMINDER2_TITLE).build();
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER2 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER1
                + AGENDA_DESC_REMINDER1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with content the same as another reminder in the address book except date and time
         * -> added
         */
        toAdd = new ReminderBuilder(REMINDER1).withDate(VALID_REMINDER2_DATE).withTime(VALID_REMINDER2_TIME).build();
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER2 + TITLE_DESC_REMINDER2
                + AGENDA_DESC_REMINDER1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with content the same as another reminder in the address book except time -> added */
        toAdd = new ReminderBuilder(REMINDER1).withTime(VALID_REMINDER2_TIME).build();
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER1 + DATE_DESC_REMINDER1 + TIME_DESC_REMINDER2
                + AGENDA_DESC_REMINDER1;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform reminder operation on the shown filtered list ---------------------------- */

        /* Case: filters the reminder list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_REMINDER);
        assertCommandSuccess(REMINDER_A);

        /* ----------------------------------- Perform invalid reminder operations ------------------------------------- */

        /* Case: add a duplicate reminder -> rejected */
        command = ReminderUtil.getReminderCommand(REMINDER_A);
        assertCommandFailure(command, ReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        /* Case: add a different reminder with same date and time -> rejected */
        toAdd = new ReminderBuilder(REMINDER2).withDate(VALID_REMINDER1_DATE).withTime(VALID_REMINDER1_TIME).build();
        command = ReminderUtil.getReminderCommand(toAdd);
        assertCommandFailure(command, ReminderCommand.MESSAGE_SAME_TIME);

        /* Case: missing title -> rejected */
        command = ReminderCommand.COMMAND_WORD + DATE_DESC_REMINDER2 + TIME_DESC_REMINDER2 + AGENDA_DESC_REMINDER2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER2 + TIME_DESC_REMINDER2 + AGENDA_DESC_REMINDER2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER2 + DATE_DESC_REMINDER2 + AGENDA_DESC_REMINDER2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));

        /* Case: missing agenda -> rejected */
        command = ReminderCommand.COMMAND_WORD + TITLE_DESC_REMINDER2 + DATE_DESC_REMINDER2 + TIME_DESC_REMINDER2;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "reminders " + ReminderUtil.getReminderDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND
                + "\n" + String.format(WrongCommandSuggestion.SUGGESTION_HEADER, ReminderCommand.COMMAND_WORD));

        /* Case: invalid title -> rejected */
        command = ReminderCommand.COMMAND_WORD + INVALID_TITLE_DESC + VALID_REMINDER2_DATE + VALID_REMINDER2_TIME
                + VALID_REMINDER2_AGENDA;
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = ReminderCommand.COMMAND_WORD + VALID_REMINDER2_TITLE + INVALID_DATE_DESC + VALID_REMINDER2_TIME
                + VALID_REMINDER2_AGENDA;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = ReminderCommand.COMMAND_WORD + VALID_REMINDER2_TITLE + VALID_REMINDER2_DATE + INVALID_TIME_DESC
                + VALID_REMINDER2_AGENDA;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid agenda  -> rejected */
        command = ReminderCommand.COMMAND_WORD + VALID_REMINDER2_TITLE + VALID_REMINDER2_DATE + VALID_REMINDER2_TIME
                + INVALID_AGENDA_DESC;
        assertCommandFailure(command, Agenda.MESSAGE_AGENDA_CONSTRAINTS);
    }

    /**
     * Executes the {@code ReminderCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ReminderCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code ReminderListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Reminder toAdd) {
        assertCommandSuccess(ReminderUtil.getReminderCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Reminder)}. Executes {@code command}
     * instead.
     * @see ReminderCommandSystemTest#assertCommandSuccess(Reminder)
     */
    private void assertCommandSuccess(String command, Reminder toAdd) {
        Model expectedModel = getModel();
        expectedModel.addReminder(toAdd);
        String expectedResultMessage = String.format(ReminderCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Reminder)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ReminderListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see ReminderCommandSystemTest#assertCommandSuccess(String, Reminder)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ReminderListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
