package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.events.ui.CompanyJobListDetailsPanelSelectionChangedEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowUpdateJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.joboffer.JobOfferContainsKeywordsPredicate;

/**
 * Finds and lists all jobs in job offers whose job details contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindJobOfferCommand extends Command {

    public static final String COMMAND_WORD = "findj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all job offers whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "COMPANY NAME "
            + "JOB "
            + "GENDER "
            + "SALARY "
            + "AGE RANGE "
            + "EDUCATION \n"
            + "Example: " + COMMAND_WORD + "n/" + "Cashier";

    private final JobOfferContainsKeywordsPredicate predicate;

    public FindJobOfferCommand(JobOfferContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyJobList(predicate);
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());

        //EventsCenter.getInstance().post(new ShowUpdateJobListRequestEvent(model));
        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW, model.getFilteredCompanyJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindJobOfferCommand // instanceof handles nulls
                && predicate.equals(((FindJobOfferCommand) other).predicate)); // state check
    }
}
