package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;

import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;

/**
 * Sorts all the job offers in the CompanyBook
 */
public class SortJobOfferCommand extends Command {

    public static final String COMMAND_WORD = "sortj";

    public static final String MESSAGE_SUCCESS = "Sorted all job offers.\n";

    public static final String MESSAGE_TAG_USAGE = "NOTE: Enter \"listC\" first to see the full list of job offers!\n"
            + "Please sort by using one of the available tags: "
            + "Company Name " + PREFIX_COMPANY_NAME
            + ", Job Titles " + PREFIX_JOB
            + ", Age Range " + PREFIX_AGE_RANGE
            + ", Education " + PREFIX_EDUCATION
            + ", Salary" + PREFIX_SALARY
            + " or sort the current order in reverse with r/ \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COMPANY_NAME;

    private static Prefix prefixToSort;

    public SortJobOfferCommand(Prefix prefix) {
        this.prefixToSort = prefix;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        model.sortJobOffers(prefixToSort);
        model.commitRecruitBook();
        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            EventsCenter.getInstance()
                    .post(new ChangeLogicStateEvent(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));

            return new CommandResult(MESSAGE_SUCCESS
                    + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                    + SelectJobCommand.MESSAGE_USAGE);
        }

        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            EventsCenter.getInstance()
                    .post(new ChangeLogicStateEvent(SelectJobCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE));
            return new CommandResult(MESSAGE_SUCCESS
                    + SelectCompanyCommand.MESSAGE_SELECT_COMPANY_SUCCESS_NEXT_STEP
                    + SelectJobCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortJobOfferCommand)) {
            return false;
        }

        // state check
        SortJobOfferCommand s = (SortJobOfferCommand) other;
        return prefixToSort.equals(s.prefixToSort);
    }

}
