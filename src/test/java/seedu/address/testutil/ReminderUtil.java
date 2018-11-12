package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AGENDA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.model.reminder.Reminder;

//@@author junweiljw
/**
 * A utility class for Reminder.
 */
public class ReminderUtil {

    /**
     * Returns a reminder command string for adding the {@code reminder}.
     */
    public static String getReminderCommand(Reminder reminder) {
        return ReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns a reminder command alias string for adding the {@code reminder}.
     */
    public static String getReminderAlias(Reminder reminder) {
        return ReminderCommand.COMMAND_ALIAS + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(Reminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + reminder.getTitle().value + " ");
        sb.append(PREFIX_DATE + reminder.getDate().value + " ");
        sb.append(PREFIX_START_TIME + reminder.getTime().value + " ");
        sb.append(PREFIX_AGENDA + reminder.getAgenda().value + " ");
        return sb.toString();
    }
}
