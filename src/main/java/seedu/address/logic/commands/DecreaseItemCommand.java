package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemQuantity;

/**
 * Decreases the quantity of an existing item in the Item List.
 */
public class DecreaseItemCommand extends Command {

    public static final String COMMAND_WORD = "decreaseItem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decreases the quantity of the item identified "
            + "by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ITEM_QUANTITY + "ITEM_QUANTITY...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM_QUANTITY + "4";

    public static final String MESSAGE_DECREASE_ITEM_SUCCESS = "Decreased quantity of Item: %1$s";

    private final Index index;
    private final ItemQuantity quantityToSubtract;

    /**
     * @param index of the item in the filtered item list to edit
     * @param quantityToSubtract to subtract from the item
     */
    public DecreaseItemCommand(Index index, ItemQuantity quantityToSubtract) {
        requireNonNull(index);
        requireNonNull(quantityToSubtract);

        this.index = index;
        this.quantityToSubtract = quantityToSubtract;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToDecrease = lastShownList.get(index.getZeroBased());
        if ((Integer.parseInt(itemToDecrease.getItemQuantity().toString()))
                <= (Integer.parseInt(quantityToSubtract.toString()))) {
            throw new CommandException(Messages.MESSAGE_NOT_ENOUGH_ITEMS);
        }
        Item decreasedItem = new Item(itemToDecrease.getItemName(),
                new ItemQuantity(Integer.toString(Integer.parseInt(itemToDecrease.getItemQuantity().toString())
                        - (Integer.parseInt(quantityToSubtract.toString())))));

        model.updateItem(itemToDecrease, decreasedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DECREASE_ITEM_SUCCESS, decreasedItem));
    }
}
