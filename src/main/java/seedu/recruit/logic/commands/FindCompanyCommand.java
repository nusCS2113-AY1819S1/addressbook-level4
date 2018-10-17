package seedu.recruit.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.CommandHistory;
import seedu.recruit.model.Model;
import seedu.recruit.model.company.CompanyNameContainsKeywordsPredicate;

/**
 * Finds and lists all companies in company book whose company name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCompanyCommand extends Command {

    public static final String COMMAND_WORD = "findC";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all companies whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "NAME \n"
            + "Example: " + COMMAND_WORD + " KFC";

    private final CompanyNameContainsKeywordsPredicate predicate;

    public FindCompanyCommand(CompanyNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCompanyCommand // instanceof handles nulls
                && predicate.equals(((FindCompanyCommand) other).predicate)); // state check
    }
}
