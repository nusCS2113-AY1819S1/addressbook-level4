package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author Hafizuddin-NUS
/**
 * Lists all leaves in the leave book to the user.
 */
public class ListLeaveCommand extends Command {

    public static final String COMMAND_WORD = "listleave";

    public static final String MESSAGE_SUCCESS = "Listed all leaves";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLeaveList(Model.PREDICATE_SHOW_ALL_LEAVES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
