package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.Model;

/**
 * Clears the divelog book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Dive log has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new DiveLog());
        model.commitDiveLog();
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
