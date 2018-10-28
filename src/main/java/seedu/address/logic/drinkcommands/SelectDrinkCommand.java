package seedu.address.logic.drinkcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.drinkcommands.exceptions.DrinkCommandException;
import seedu.address.model.DrinkModel;
import seedu.address.model.drink.Drink;

/**
 * Selects a drink identified using its displayed index from the inventory list.
 */
public class SelectDrinkCommand extends DrinkCommand {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the drink identified by the index number used in the displayed drink list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_DRINK_SUCCESS = "Selected Drink: %1$s";

    private final Index targetIndex;

    public SelectDrinkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public DrinkCommandResult execute(DrinkModel model, CommandHistory history) throws DrinkCommandException {
        requireNonNull(model);

        List<Drink> filteredDrinkList = model.getFilteredDrinkList();

        if (targetIndex.getZeroBased() >= filteredDrinkList.size()) {
            throw new DrinkCommandException(Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new DrinkCommandResult(String.format(MESSAGE_SELECT_DRINK_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectDrinkCommand // instanceof handles nulls
                && targetIndex.equals(((SelectDrinkCommand) other).targetIndex)); // state check
    }
}
