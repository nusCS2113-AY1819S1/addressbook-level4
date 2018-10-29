package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Selects a group identified using it's displayed index from the address book.
 */
public class SelectGroupCommand extends Command {
    public static final String COMMAND_WORD = "selectgroup";
    public static final String COMMAND_WORD_2 = "sg";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";

    private final Index targetIndex;

    public SelectGroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Group> filteredGroupList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= filteredGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectGroupCommand // instanceof handles nulls
                && targetIndex.equals(((SelectGroupCommand) other).targetIndex)); // state check
    }

}
