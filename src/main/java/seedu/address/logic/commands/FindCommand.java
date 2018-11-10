package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.drink.Date;
import seedu.address.model.drink.NameContainsKeywordsPredicate;
import seedu.address.model.drink.Quantity;


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
    private final Date date;
    private final Quantity quantity;
    private final boolean modifier;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.date = null;
        this.quantity = null;
        this.modifier = false;
    }

    public FindCommand(Date date, Boolean modifier) {
        this.predicate = null;
        this.date = date;
        this.quantity = null;
        this.modifier = modifier;
    }

    public FindCommand(Quantity quantity, Boolean modifier) {
        this.predicate = null;
        this.date = null;
        this.quantity = quantity;
        this.modifier = modifier;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (predicate != null) {
            model.updateFilteredDrinkList(predicate);
        } else if (date != null && modifier == false) {
            model.updateFilteredDrinkList()
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
