package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.drinkcommands.exceptions.DrinkCommandException;
import seedu.address.model.DrinkModel;
import seedu.address.model.drink.Drink;

/**
 * Deletes a drink identified using its displayed index from the inventory list.
 */
public class DeleteDrinkCommand extends DrinkCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the drink identified by the index number used in the displayed drink list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DRINK_SUCCESS = "Deleted Drink: %1$s";

    private final Index targetIndex;

    public DeleteDrinkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) throws DrinkCommandException {
        requireNonNull(model);
        List<Drink> lastShownList = model.getFilteredDrinkList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new DrinkCommandException(Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
        }

        Drink drinkToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDrink(drinkToDelete);
        return new DrinkCommandResult(String.format(MESSAGE_DELETE_DRINK_SUCCESS, drinkToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDrinkCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDrinkCommand) other).targetIndex)); // state check
    }
}
