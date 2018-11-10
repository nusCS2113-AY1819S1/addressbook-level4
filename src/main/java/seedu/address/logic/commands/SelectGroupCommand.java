package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Selects a group via index.
 */
public class SelectGroupCommand extends Command {
    public static final String COMMAND_WORD = "selectgroup";
    public static final String COMMAND_WORD_2 = "sg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";
    public static final String LOG_COMMAND_SUCCESS = "Group has been selected";
    public static final String LOG_INVALID_GROUP_INDEX = "Invalid group index detected";

    private static final Logger logger = LogsCenter.getLogger(SelectGroupCommand.class);

    private final Index targetIndex;

    /**
     * Receives index for selecting.
     *
     * @param targetIndex Group index to select.
     */
    public SelectGroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Selects a group.
     *
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return Successful command result.
     * @throws CommandException If index is invalid.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> filteredGroupList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= filteredGroupList.size()) {
            logger.log(Level.WARNING, LOG_INVALID_GROUP_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
        logger.log(Level.INFO, LOG_COMMAND_SUCCESS);
        return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));

    }

    /**
     * Returns true if the objects are the same.
     *
     * @param other Object to compare with.
     * @return Result of comparison.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectGroupCommand // instanceof handles nulls
                && targetIndex.equals(((SelectGroupCommand) other).targetIndex)); // state check
    }

}
