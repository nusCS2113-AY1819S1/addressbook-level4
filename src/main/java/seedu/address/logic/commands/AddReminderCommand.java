package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * Adds a reminder to the ProductInfo Book.
 */

public class AddReminderCommand extends Command {
    public static final String COMMAND_WORD = "addreminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder.\nExample: "
            + "addreminder "
            + PREFIX_TIME
            + "2018/08/15 15:06:00 "
            + PREFIX_REMINDER_MESSAGE
            + "replace expired milk in aisle 6.";

    public static final String MESSAGE_SUCCESS = "Reminder \"%s\" successfully recorded for time %s";

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
            throw new CommandException(e.getExceptionMessage() + ". Upon adding this reminder");
        } catch (DuplicateReminderException e) {
            throw new CommandException(e.getExceptionMessage());
        }
        model.commitSalesHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getReminderMessage(), toAdd.getReminderTime()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReminderCommand // instanceof handles nulls
                && toAdd.equals(((AddReminderCommand) other).toAdd));
    }
}
