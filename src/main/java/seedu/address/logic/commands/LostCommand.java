package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;


import java.util.List;
import java.util.ArrayList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Quantity;

import java.util.List;

public class LostCommand extends Command{
    public static final String COMMAND_WORD = "lost";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lost a item from the stock list identified  "
            +"by the index number used in the displayed item list"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUANTITY + "25";


    public static final String MESSAGE_LOST_ITEM_SUCCESS = "Lost Item: %1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "The lost quantity input is invalid";
    public static final String MESSAGE_INVALID_LOST_FIELD = "The lost description is invalid";

    private final Index targetIndex;
    private final LostDescriptor lostDescriptor;

    public LostCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        requireNonNull(lostDescriptor);
        this.targetIndex = targetIndex;
        this.lostDescriptor = new LostDescriptor(lostDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToLost = lastShownList.get(targetIndex.getZeroBased());
        Item lostItem = createLostItem(itemToLost,LostDescriptor);

        if (!itemToLost.isSameItem(lostItem) && model.hasItem(lostItem)) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        model.updateItem(itemToLost,lostItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_LOST_ITEM_SUCCESS, lostItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToLost}
     * edited with {@code LostDescriptor}.
     */
    private static Item createLostItem(Item itemToLost, LostDescriptor lostDescriptor) {
        assert itemToLost != null;

        Quantity updatedQuantity = (itemToLost.getQuantity()-LostDescriptor.getQuantity()).orElse(itemToLost.getQuantity());


        return new Item(itemToLost.getName(), updatedQuantity, itemToLost.getMinQuantity(), itemToLost.getTags());
    }

    public static class LostDescriptor {
        private Integer lostQuantity;
        public LostDescriptor(){}

        public LostDescriptor(LostDescriptor toCopy) {
            setQuantity(toCopy.lostQuantity);

        }
        public static void setQuantity(Integer lostQuantity) {this.lostQuantity=lostQuantity;}
        public Integer getQuantity(){return lostQuantity;}
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LostCommand // instanceof handles nulls
                && targetIndex.equals(((LostCommand) other).targetIndex)); // state check
    }
}
