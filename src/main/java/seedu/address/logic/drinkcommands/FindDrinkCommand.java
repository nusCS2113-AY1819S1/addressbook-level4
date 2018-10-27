package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.DrinkModel;
import seedu.address.model.drink.NameContainsKeywordsPredicate;

public class FindDrinkCommand extends DrinkCommand {
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all drinks whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " coca cola pepsi fanta";

    private final NameContainsKeywordsPredicate predicate;

    public FindDrinkCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDrinkList(predicate);
        return new DrinkCommandResult(
                String.format(Messages.MESSAGE_DRINKS_LISTED_OVERVIEW, model.getFilteredDrinkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDrinkCommand // instanceof handles nulls
                && predicate.equals(((FindDrinkCommand) other).predicate)); // state check
    }
}
