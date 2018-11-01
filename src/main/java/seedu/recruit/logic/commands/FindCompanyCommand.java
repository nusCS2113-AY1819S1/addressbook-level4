package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.recruit.commons.core.EventsCenter;
import seedu.recruit.commons.core.Messages;
import seedu.recruit.commons.events.ui.ShowCompanyBookRequestEvent;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.company.CompanyContainsKeywordsPredicate;

/**
 * Finds and lists all companies in company book whose company name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCompanyCommand extends Command {

    public static final String COMMAND_WORD = "findC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all companies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_COMPANY_NAME + "COMPANY NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + PREFIX_COMPANY_NAME + "Hanbaobao Pte Ltd";

    private final CompanyContainsKeywordsPredicate predicate;

    public FindCompanyCommand(CompanyContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        EventsCenter.getInstance().post(new ShowCompanyBookRequestEvent());
        return new CommandResult(
                String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCompanyCommand // instanceof handles nulls
                && predicate.equals(((FindCompanyCommand) other).predicate)); // state check
    }
}
