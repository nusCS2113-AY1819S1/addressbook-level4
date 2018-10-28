package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book by specified criteria.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_UNIMPLEMENTED = "Not implemented yet";
    // public static final String MESSAGE_SUCCESS = "Sorted as asked";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_UNIMPLEMENTED);
    }
}
