package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book by specified criteria.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_ARGUMENTS = "Parameter: %1$s";
    // public static final String MESSAGE_SUCCESS = "Sorted as asked";

    private final String param;

    public SortCommand(String param) {
        this.param = param;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, param));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }
        // state check
        SortCommand e = (SortCommand) other;
        return param.equals(e.param);
    }
}
