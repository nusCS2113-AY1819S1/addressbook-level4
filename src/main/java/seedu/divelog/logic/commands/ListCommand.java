package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;

/**
 * Lists all dives in the divelog book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all dives";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        requireNonNull(model);
        model.updateFilteredDiveList(Model.PREDICATE_SHOW_ALL_DIVES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
