package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.Prefix;
import seedu.recruit.model.Model;

/**
 * Sorts all the job offers in the CompanyBook
 */
public class SortJobOfferCommand extends Command {

    public static final String COMMAND_WORD = "sortj";

    public static final String MESSAGE_SUCCESS = "Sorted all job offers";

    public static final String MESSAGE_TAG_USAGE = "Please sort by using one of the available tags: "
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
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        model.sortJobOffers(prefixToSort);
        model.commitCompanyBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
