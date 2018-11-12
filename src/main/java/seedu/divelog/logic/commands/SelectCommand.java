package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.divelog.commons.core.EventsCenter;
import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.commons.events.ui.JumpToListRequestEvent;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.logic.commands.exceptions.CommandException;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.DiveSession;

/**
 * Selects a dive session identified using it's displayed index from the divelog book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the dive session identified by the index number used in the displayed dive list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_DIVE_SESSION_SUCCESS = "Selected Dive session: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<DiveSession> filteredDiveSessionList = model.getFilteredDiveList();

        if (targetIndex.getZeroBased() >= filteredDiveSessionList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIVE_DISPLAYED_INDEX);
        }
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_DIVE_SESSION_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
