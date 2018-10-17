package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MESSAGE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateReminderException;

/**
 * Adds a reminder to the Address Book.
 */

public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "addreminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder. Example: "
            + "addreminder "
            + PREFIX_TIME
            + "2018/08/15 15:06:00"
            + PREFIX_REMINDER_MESSAGE
            + "replace expired milk in aisle 6.";

    private static final String MESSAGE_SUCCESS = "Reminder \"%s\" successfully recorded for time %s";

    private final Reminder toAdd;

    public AddReminderCommand(Reminder toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            model.addReminder(toAdd);
        } catch (InvalidTimeFormatException e) {
            return new CommandResult(e.getExceptionMessage() + ". Upon adding this reminder");
        } catch (DuplicateReminderException e) {
            return new CommandResult(e.getExceptionMessage());
        }
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS,toAdd.getMessage(),toAdd.getTime()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
