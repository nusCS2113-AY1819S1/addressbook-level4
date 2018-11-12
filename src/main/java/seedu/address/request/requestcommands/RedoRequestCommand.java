//@@author guiyong96
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.request.requestmodel.RequestModel.PREDICATE_SHOW_ALL_REQUESTS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Reverts the {@code model}'s RequestList to its previously undone state.
 */
public class RedoRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);

        if (!requestModel.canRedoRequests()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        requestModel.redoRequests();
        requestModel.updateFilteredRequestList(PREDICATE_SHOW_ALL_REQUESTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
