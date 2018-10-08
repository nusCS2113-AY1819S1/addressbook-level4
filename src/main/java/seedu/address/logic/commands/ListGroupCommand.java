package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all groups in the address book to the user.
 */
public class ListGroupCommand extends Command {
    public static final String COMMAND_WORD = "listgroup";
    public static final String COMMAND_WORD_2 = "lg";
    public static final String MESSAGE_SUCCESS = "Listed all groups";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "List group command not implemented yet";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
