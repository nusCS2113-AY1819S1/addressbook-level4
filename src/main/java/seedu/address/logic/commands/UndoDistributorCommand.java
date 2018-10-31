package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DISTRIBUTORS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
*/
public class UndoDistributorCommand extends Command {

    public static final String COMMAND_WORD = "undoDistributor";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoDistributorBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoDistributorBook();
        model.updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
