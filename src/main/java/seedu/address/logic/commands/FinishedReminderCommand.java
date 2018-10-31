package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.NoSuchElementException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This class removes a reminder whose task has been finished
 */
public class FinishedReminderCommand extends Command {
    public static final String COMMAND_WORD = "finished";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Marks a reminder as completed and removes it from storage"
            + "Parameters: "
            + PREFIX_TIME + "REMINDER TIME"
            + "Example: "
            + "finished time/ 20/12/2015 20:00:00";

    private static final String MESSAGE_SUCCESS = "Reminder at time %s successfully removed";
    private static final String FAILURE_NO_SUCH_REMINDER = "No such reminder has been set";
    private final String toRemoveReminderTime;

    public FinishedReminderCommand(String toRemoveReminderTime) {
        this.toRemoveReminderTime = toRemoveReminderTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            model.removeReminder(toRemoveReminderTime);
        } catch (InvalidTimeFormatException e) {
            return new CommandResult(e.getExceptionMessage() + "Upon trying to remove this reminder.");
        } catch (NoSuchElementException e) {
            return new CommandResult(FAILURE_NO_SUCH_REMINDER);
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRemoveReminderTime));
    }
}
