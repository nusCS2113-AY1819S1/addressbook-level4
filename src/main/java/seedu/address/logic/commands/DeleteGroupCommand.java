package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a group identified using it's displayed index from the address book.
 */
public class DeleteGroupCommand extends Command {

    public static final String COMMAND_WORD = "deletegroup";
    public static final String COMMAND_WORD_2 = "dg";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$s";

    private final Index targetIndex;

    public DeleteGroupCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the index number used in the displayed group list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteGroupCommand) other).targetIndex)); // state check
    }

}
