package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.commons.events.ui.ShowUpdatedCompanyJobListRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.joboffer.JobOfferContainsFilterKeywordsPredicate;

/**
 * Filters and lists all jobs in job offers whose job details contains all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterJobOfferCommand extends Command {

    public static final String COMMAND_WORD = "filterj";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all job offers whose names contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_COMPANY_NAME + "COMPANY NAME "
            + PREFIX_JOB + "JOB "
            + PREFIX_GENDER + "GENDER "
            + PREFIX_SALARY + "SALARY "
            + PREFIX_AGE_RANGE + "AGE RANGE "
            + PREFIX_EDUCATION + "EDUCATION \n"
            + "Example: " + COMMAND_WORD + PREFIX_COMPANY_NAME + "KFC " + PREFIX_JOB + "Cashier" ;

    private final JobOfferContainsFilterKeywordsPredicate predicate;

    public FilterJobOfferCommand(JobOfferContainsFilterKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        model.updateFilteredCompanyJobList(predicate);
        EventsCenter.getInstance().post(new ShowUpdatedCompanyJobListRequestEvent(
                model.getFilteredCompanyJobList().size()));
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent()); // switches if user isn't on Company Book
        return new CommandResult(
                String.format(Messages.MESSAGE_JOBS_LISTED_OVERVIEW, model.getFilteredCompanyJobList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterJobOfferCommand // instanceof handles nulls
                && predicate.equals(((FilterJobOfferCommand) other).predicate)); // state check
    }
}
