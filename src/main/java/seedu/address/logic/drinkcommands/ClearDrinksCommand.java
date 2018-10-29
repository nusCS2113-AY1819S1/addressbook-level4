package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DrinkModel;
import seedu.address.model.InventoryList;

/**
 * Clears the inventory list.
 */
public class ClearDrinksCommand extends DrinkCommand {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory list has been cleared!";

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new InventoryList());
        return new DrinkCommandResult(MESSAGE_SUCCESS);
    }
}
