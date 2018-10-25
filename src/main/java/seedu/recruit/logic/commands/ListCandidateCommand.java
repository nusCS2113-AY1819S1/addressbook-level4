package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * Lists all candidates in the recruit book to the user.
 * If the user is viewing Company Book, it will switch to Candidate Book.
 */
public class ListCandidateCommand extends Command {

    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all candidates";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCandidateList(PREDICATE_SHOW_ALL_PERSONS);
        MainWindow.switchToCandidateBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
