//@@author guiyong96
package seedu.address.request.requestcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.request.Request;
import seedu.address.request.requestmodel.RequestModel;

/**
 * Deletes a request identified using its displayed index from the RequestList.
 */
public class DeleteRequestCommand extends CommandSecondary {

    public static final String COMMAND_WORD = "deleterequest";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the request identified by the index number used in the displayed request list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REQUEST_SUCCESS = "Request deleted successfully!";

    private final Index targetIndex;

    public DeleteRequestCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(RequestModel requestModel, CommandHistory history) throws CommandException {
        requireNonNull(requestModel);
        List<Request> lastShownList = requestModel.getFilteredRequestList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Request requestToDelete = lastShownList.get(targetIndex.getZeroBased());
        requestModel.deleteRequest(requestToDelete);
        requestModel.commitRequests();
        return new CommandResult(String.format(MESSAGE_DELETE_REQUEST_SUCCESS, requestToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRequestCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRequestCommand) other).targetIndex)); // state check
    }
}
