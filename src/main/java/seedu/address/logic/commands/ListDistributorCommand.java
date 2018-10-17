package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DISTRIBUTORS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListDistributorCommand extends Command {

    public static final String COMMAND_WORD = "listDistributor";

    public static final String MESSAGE_SUCCESS = "Listed all distributors";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
