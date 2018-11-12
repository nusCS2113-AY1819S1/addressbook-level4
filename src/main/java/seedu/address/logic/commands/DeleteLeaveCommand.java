package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;
import seedu.address.session.SessionManager;

//@@author Hafizuddin-NUS
/**
 * Deletes a leave identified using it's displayed index from the leave book.
 */
public class DeleteLeaveCommand extends Command {

    public static final String COMMAND_WORD = "deleteleave";
    public static final String COMMAND_ALIAS = "dl";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the leave identified by the index number used in the displayed leave list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LEAVE_SUCCESS = "Deleted Leave: %1$s";
    public static final String MESSAGE_INVALID_LEAVE_DELETE = "Not authorized to delete other user's leave application";

    private final Index targetIndex;

    public DeleteLeaveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        SessionManager sessionManager = SessionManager.getInstance(model);
        List<Leave> lastShownList = model.getFilteredLeaveList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        Leave leaveToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (sessionManager.getLoggedInPriorityLevel().priorityLevelCode != 0
                && !(leaveToDelete.getEmployeeId().nric.equals(sessionManager.getLoggedInSessionNric().nric))
                && (sessionManager.getLoggedInPriorityLevel().priorityLevelCode
                >= leaveToDelete.getPriorityLevel().priorityLevelCode)) {
            throw new CommandException(MESSAGE_INVALID_LEAVE_DELETE);
        }

        model.deleteLeave(leaveToDelete);
        model.commitLeaveList();
        return new CommandResult(String.format(MESSAGE_DELETE_LEAVE_SUCCESS, leaveToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLeaveCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteLeaveCommand) other).targetIndex)); // state check
    }
}
