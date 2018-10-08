package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CandidateBook;
import seedu.address.model.Model;

/**
 * Clears the Candidatebook.
 */
public class ClearCandidateBookCommand extends Command {

    public static final String COMMAND_WORD = "clearc";
    public static final String MESSAGE_SUCCESS = "CandidateBook has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetCandidateData(new CandidateBook());
        model.commitCandidateBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
