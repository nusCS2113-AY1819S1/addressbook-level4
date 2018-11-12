//@@author guiyong96
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.request.requestmodel.RequestModel.PREDICATE_SHOW_ALL_REQUESTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Reverts the {@code model}'s RequestList to its previous state.
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
