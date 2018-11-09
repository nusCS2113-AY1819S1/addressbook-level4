package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Deletes a group identified using it's displayed index from the address book.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "deletegroup";
    public static final String COMMAND_WORD_2 = "dg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";
    public static final String LOG_COMMIT = "Version Committed";
    public static final String LOG_COMMAND_SUCCESS = "Group has been deleted";
    public static final String LOG_INVALID_GROUP_INDEX = "Invalid group index detected";

    private static final Logger logger = LogsCenter.getLogger(DeleteGroupCommand.class);

    private final Index targetIndex;


    public DeleteGroupCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, LOG_INVALID_GROUP_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        Group groupToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteGroup(groupToDelete);
        logger.log(Level.INFO, LOG_COMMAND_SUCCESS);
        model.commitAddressBook();
        logger.log(Level.INFO, LOG_COMMIT);
        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteGroupCommand) other).targetIndex)); // state check
    }

}
