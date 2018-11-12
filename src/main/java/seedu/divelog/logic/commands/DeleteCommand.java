package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.divelog.commons.core.Messages.MESSAGE_ERROR_LIMIT_EXCEED;

import java.util.List;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.logic.pressuregroup.exceptions.LimitExceededException;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

/**
 * Deletes a dive session identified using it's displayed index from the divelog book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the dive session identified by the index number used in the displayed dive list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DIVE_SESSION_SUCCESS = "Deleted dive session: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);

        List<DiveSession> lastShownList = model.getFilteredDiveList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);
        }

        DiveSession diveToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteDiveSession(diveToDelete);
        } catch (seedu.divelog.model.dive.exceptions.DiveNotFoundException e) {
            e.printStackTrace();
            //TODO: Add error message
        }

        try {
            model.recalculatePressureGroups();
            model.commitDiveLog();
            if (model.getPlanningMode()) {
                model.plannerCountPlus();
            }
            return new CommandResult(String.format(MESSAGE_DELETE_DIVE_SESSION_SUCCESS, diveToDelete));
        } catch (LimitExceededException le) {
            //Shouldn't actually be triggered
            return new CommandResult(MESSAGE_ERROR_LIMIT_EXCEED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
