package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_DRINKS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.drink.DateCompareBeforePredicate;
import seedu.address.model.drink.NameContainsKeywordsPredicate;
import seedu.address.model.drink.QuantityCompareLessPredicate;


/**
 * Finds and lists all drinks in inventory list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all drinks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " coca cola pepsi fanta";

    private final NameContainsKeywordsPredicate predicate;
    private final DateCompareBeforePredicate datePredicate;
    private final QuantityCompareLessPredicate quantityPredicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.datePredicate = null;
        this.quantityPredicate = null;
    }

    public FindCommand(DateCompareBeforePredicate date) {
        this.predicate = null;
        this.datePredicate = date;
        this.quantityPredicate = null;
    }

    public FindCommand(QuantityCompareLessPredicate quantity) {
        this.predicate = null;
        this.datePredicate = null;
        this.quantityPredicate = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (predicate != null) {
            model.updateFilteredDrinkList(predicate);
        } else if (datePredicate != null) {
            try {
                model.updateFilteredDrinkList(datePredicate);
            } catch (NullPointerException e) {
                model.updateFilteredDrinkList(PREDICATE_SHOW_NO_DRINKS);
                return new CommandResult(
                        String.format(Messages.MESSAGE_NO_DRINK_BATCHES, model.getFilteredDrinkList().size()));
            }
        } else if (quantityPredicate != null) {
            try {
                model.updateFilteredDrinkList(quantityPredicate);
            } catch (NullPointerException e) {
                model.updateFilteredDrinkList(PREDICATE_SHOW_NO_DRINKS);
                return new CommandResult(
                        String.format(Messages.MESSAGE_NO_DRINK_BATCHES, model.getFilteredDrinkList().size()));
            }
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_DRINKS_LISTED_OVERVIEW, model.getFilteredDrinkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
