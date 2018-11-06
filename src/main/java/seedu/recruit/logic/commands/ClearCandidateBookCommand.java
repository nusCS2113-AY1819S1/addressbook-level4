package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.Model;

/**
 * Clears the Candidatebook.
 */
public class ClearCandidateBookCommand extends Command {

    public static final String COMMAND_WORD = "clearc";
    public static final String MESSAGE_SUCCESS = "CandidateBook has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears Candidate Book.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        model.resetCandidateData(new CandidateBook());
        model.commitCandidateBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
