package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Find and display the persons with the same tag
 * Allows one to find the friends of similar tags
 */

public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";
    public static final String COMMAND_WORD_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified tags (case-sensitive) and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " friends";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
