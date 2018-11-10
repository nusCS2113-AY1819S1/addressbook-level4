package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.events.logic.ChangeLogicStateEvent;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.FilterCompanyCommandParser;

import seedu.recruit.model.Model;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.model.company.CompanyContainsFilterKeywordsPredicate;

/**
 * Filters and lists all companies in company book whose company name contains all of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCompanyCommand extends Command {

    public static final String COMMAND_WORD = "filterC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all companies whose names contain all of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_COMPANY_NAME + "COMPANY NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_COMPANY_NAME + " Hanbaobao Pte Ltd "
            + PREFIX_PHONE + " 63336222";

    private final CompanyContainsFilterKeywordsPredicate predicate;
    private final String userInput;

    public FilterCompanyCommand(CompanyContainsFilterKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.userInput = FilterCompanyCommandParser.getUserInput();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history, UserPrefs userPrefs) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);

        if (ShortlistCandidateInitializationCommand.isShortlisting()) {
            EventsCenter.getInstance()
                    .post(new ChangeLogicStateEvent(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST));

            return new CommandResult(String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW,
                    model.getFilteredCompanyList().size())
                    + ShortlistCandidateInitializationCommand.MESSAGE_NEXT_STEP
                    + SelectCompanyCommand.MESSAGE_USAGE);
        }

        if (DeleteShortlistedCandidateInitializationCommand.isDeleting()) {
            EventsCenter.getInstance()
                    .post(new ChangeLogicStateEvent(SelectCompanyCommand.COMMAND_LOGIC_STATE_FOR_SHORTLIST_DELETE));

            return new CommandResult(String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW,
                    model.getFilteredCompanyList().size())
                    + DeleteShortlistedCandidateInitializationCommand.MESSAGE_NEXT_STEP
                    + SelectCompanyCommand.MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        return new CommandResult("Company Book showing: " + userInput + "\n"
                + String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCompanyCommand // instanceof handles nulls
                && predicate.equals(((FilterCompanyCommand) other).predicate)); // state check
    }
}
