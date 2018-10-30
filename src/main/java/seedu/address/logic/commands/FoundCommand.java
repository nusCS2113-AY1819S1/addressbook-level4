package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Loststatus;
import seedu.address.model.item.Quantity;


//@@author HeHaowei

/**
 * Found an existing item in the stock list.
 */

public class FoundCommand extends Command {
    public static final String COMMAND_WORD = "found";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Found a item from the stock list identified  "
            + "by the index number used in the displayed item list"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_QUANTITY + "QUANTITY\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "25";


    public static final String MESSAGE_FOUND_ITEM_SUCCESS = "Found Item: %1$s";
    public static final String MESSAGE_INVALID_QUANTITY = "The found quantity input is invalid";

    private final Index targetIndex;
    private final FoundDescriptor foundDescriptor;

    public FoundCommand(Index targetIndex, FoundDescriptor foundDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(foundDescriptor);
        this.targetIndex = targetIndex;
        this.foundDescriptor = new FoundDescriptor(foundDescriptor);

    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToFound = lastShownList.get(targetIndex.getZeroBased());
        Item foundItem = createFoundItem(itemToFound, foundDescriptor);

        if (!itemToFound.isSameItem(foundItem) && model.hasItem(foundItem)) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        model.updateItem(itemToFound, foundItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_FOUND_ITEM_SUCCESS, foundItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToFound}
     * edited with {@code FoundDescriptor}.
     */
    private static Item createFoundItem(Item itemToFound, FoundDescriptor foundDescriptor)
            throws CommandException {
        assert itemToFound != null;
        Loststatus currentLoststatus = itemToFound.getLoststatus();
        Loststatus updatedLoststatus;
        Integer updatedLost = currentLoststatus.getLoststatusLost();
        Integer updatedFound = currentLoststatus.getLoststatusFound();

        Integer updatedValue = foundDescriptor.getFoundQuantity();
        Integer initialValue = itemToFound.getQuantity().toInteger();

        updatedLost -= updatedValue;
        updatedFound += updatedValue;
        if (updatedLost < 0) {
            throw new CommandException(MESSAGE_INVALID_QUANTITY);
        }
        updatedLoststatus = new Loststatus(updatedLost, updatedFound);

        Quantity updatedQuantity = new Quantity(Integer.toString(initialValue + updatedValue));



        return new Item(itemToFound.getName(), updatedQuantity,
                itemToFound.getMinQuantity(), updatedLoststatus, itemToFound.getTags());
    }

    /**
     * Stores the details to lost the item with.
     */

    public static class FoundDescriptor {
        private Integer foundQuantity;

        public FoundDescriptor() {}

        public FoundDescriptor(FoundDescriptor toCopy) {
            setFoundQuantity(toCopy.foundQuantity);

        }
        public void setFoundQuantity(Integer foundQuantity) {
            this.foundQuantity = foundQuantity; }

        public Integer getFoundQuantity() {
            return foundQuantity; }

    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FoundCommand // instanceof handles nulls
                && targetIndex.equals(((FoundCommand) other).targetIndex)); // state check
    }
}
