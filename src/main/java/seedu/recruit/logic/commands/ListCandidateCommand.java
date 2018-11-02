package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCandidateBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowUpdateCompanyJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;

/**
 * Lists all candidates in the recruit book to the user.
 * If the user is viewing Company Book, it will switch to Candidate Book.
 */
public class ListCandidateCommand extends Command {

    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all candidates.\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all candidates.\n";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new ShowUpdateCompanyJobListRequestEvent());
        EventsCenter.getInstance().post(new ShowCandidateBookRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
