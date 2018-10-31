package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.QuantityContainsNumberPredicate;

/**
 * Finds and lists all books in BookInventory with quantity less than or equal to given number
 */
public class CheckCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks all book with quantity less than or "
            + "equal to the quantity specified and displays them as a list with index numbers.\n"
            + "Parameters: NUMBER\n"
            + "Example: " + COMMAND_WORD + " 4";

    private final QuantityContainsNumberPredicate predicate;

    public CheckCommand(QuantityContainsNumberPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.sortBooksUsingQuantity();
        model.updateFilteredBookList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CheckCommand // instanceof handles nulls
                && predicate.equals(((CheckCommand) other).predicate)); // state check
    }
}
