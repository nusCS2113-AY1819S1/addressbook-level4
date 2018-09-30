package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;

/**
 * Lists all persons in the divelog book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDiveList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
