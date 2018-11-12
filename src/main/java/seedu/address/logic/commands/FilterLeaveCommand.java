package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.leave.NricContainsKeywordsPredicate;

//@@author Hafizuddin-NUS
/**
 * Finds and lists all leaves in leave list whose NRIC contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterLeaveCommand extends Command {

    public static final String COMMAND_WORD = "filterleave";
    public static final String COMMAND_ALIAS = "fl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all leaves whose nric contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD";

    private final NricContainsKeywordsPredicate predicate;

    public FilterLeaveCommand(NricContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLeaveList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_LEAVES_LISTED_OVERVIEW, model.getFilteredLeaveList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterLeaveCommand // instanceof handles nulls
                && predicate.equals(((FilterLeaveCommand) other).predicate)); // state check
    }
}
