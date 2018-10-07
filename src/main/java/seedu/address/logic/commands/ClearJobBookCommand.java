package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.JobBook;
import seedu.address.model.Model;

/**
 * Clears the Jobbook.
 */

public class ClearJobBookCommand extends Command {
    public static final String COMMAND_WORD = "clearj";
    public static final String MESSAGE_SUCCESS = "JobBook has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetJobOfferData(new JobBook());
        model.commitJobBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
