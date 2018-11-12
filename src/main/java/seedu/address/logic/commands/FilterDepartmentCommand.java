package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.DepartmentContainsKeywordsPredicate;

//@@author Woonhian
/**
 * Finds and lists all persons in address book whose department contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterDepartmentCommand extends Command {

    public static final String COMMAND_WORD = "filterdepartment";
    public static final String COMMAND_ALIAS = "fd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose department contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "The keyword 'management' will not be accepted to prevent listing out of all departments.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " junior";

    private final DepartmentContainsKeywordsPredicate predicate;

    public FilterDepartmentCommand(DepartmentContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterDepartmentCommand // instanceof handles nulls
                && predicate.equals(((FilterDepartmentCommand) other).predicate)); // state check
    }
}
