package seedu.address.model.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.request.RequestModel.PREDICATE_SHOW_ALL_REQUESTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Reverts the {@code model}'s BookInventory to its previous state.
 */
public class UndoRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        if (!requestModel.canUndoRequests()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        requestModel.undoRequests();
        requestModel.updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
