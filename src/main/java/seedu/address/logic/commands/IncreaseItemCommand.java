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
 * Edits the details of an existing member in the address book.
 */
public class IncreaseItemCommand extends Command {

    public static final String COMMAND_WORD = "increaseItem";
    public static final String COMMAND_ALIAS = "iI";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increases the quantity of the item identified "
            + "by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ITEM_QUANTITY + "ITEM_QUANTITY...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM_QUANTITY + "4";

    public static final String MESSAGE_INCREASE_ITEM_SUCCESS = "Increased quantity of Item: %1$s";

    private final Index index;
    private final ItemQuantity quantityToAdd;

    /**
     * @param index of the item in the filtered item list to edit
     * @param quantityToAdd to add to the item
     */
    public IncreaseItemCommand(Index index, ItemQuantity quantityToAdd) {
        requireNonNull(index);
        requireNonNull(quantityToAdd);

        this.index = index;
        this.quantityToAdd = quantityToAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToIncrease = lastShownList.get(index.getZeroBased());
        Item increasedItem = new Item(itemToIncrease.getItemName(),
                new ItemQuantity(Integer.toString((Integer.parseInt(quantityToAdd.toString()))
                        + Integer.parseInt(itemToIncrease.getItemQuantity().toString()))));

        model.updateItem(itemToIncrease, increasedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_INCREASE_ITEM_SUCCESS, increasedItem));
    }
}
