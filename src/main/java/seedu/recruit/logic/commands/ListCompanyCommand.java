package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.recruit.model.Model.PREDICATE_SHOW_ALL_JOBOFFERS;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.ui.MainWindow;

/**
 * Lists all companies, as well as all job offers, in the recruit book to the user.
 * If the user is viewing Candidate Book, it will switch to Company Book.
 */
public class ListCompanyCommand extends Command {

    public static final String COMMAND_WORD = "listC";

    public static final String MESSAGE_SUCCESS = "Listed all companies and all job offers";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        model.updateFilteredCompanyJobList(PREDICATE_SHOW_ALL_JOBOFFERS);
        MainWindow.switchToCompanyBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
