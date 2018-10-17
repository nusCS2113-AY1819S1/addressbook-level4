package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORIGINAL_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;


/**
 * Updates the status of an existing item in the stock list.
 */

public class ChangeStatusCommand extends Command {
    public static final String COMMAND_WORD = "changeStatus";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the status of the item identified "
            + "by the index number used in the displayed item list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_ORIGINAL_STATUS + "ORIGINAL STATUS] "
            + "[" + PREFIX_NEW_STATUS + "NEW STATUS] "
            + "Example: " + COMMAND_WORD + "1"
            + PREFIX_QUANTITY + "5 "
            + PREFIX_ORIGINAL_STATUS + "Ready"
            + PREFIX_NEW_STATUS + "Faulty";
    public static final String MESSAGE_CHANG_STATUS_SUCCESS = "Changed Status: %1$s";
    public static final String MESSAGE_INVALID_STATUS_QUANTITY = "The change status quantity input is invalid";
    public static final String MESSAGE_INVALID_STATUS_FIELD = "The status description is invalid";


    private final Index index;
    private final ChangeStatusDescriptor changeStatusDescriptor;
    public ChangeStatusCommand(Index index, ChangeStatusDescriptor changeStatusDescriptor) {
        requireNonNull(index);
        requireNonNull(changeStatusDescriptor);
        this.index = index;
        this.changeStatusDescriptor = new ChangeStatusDescriptor(changeStatusDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToUpdate = lastShownList.get(index.getZeroBased());

        Item updatedItem = createUpdatedItem(itemToUpdate, changeStatusDescriptor);

        if (!itemToUpdate.isSameItem(updatedItem) && model.hasItem(updatedItem)) {
            throw new CommandException(MESSAGE_INVALID_STATUS_QUANTITY);
        }
        model.updateItem(itemToUpdate, updatedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(String.format(MESSAGE_CHANG_STATUS_SUCCESS, updatedItem));


    }
    /**
     * Creates and returns a {@code Item} with the details of {@code itemToUpdate}
     * edited with {@code changeStatusDescriptor}.
     */
    private static Item createUpdatedItem(Item itemToUpdate,
                                          ChangeStatusDescriptor changeStatusDescriptor) throws CommandException {
        assert itemToUpdate != null;
        List<Integer> currentStatus = itemToUpdate.getStatus();
        List<Integer> updatedStatus = new ArrayList<>();
        Integer updatedReady = currentStatus.get(0);
        Integer updatedOnLoan = currentStatus.get(1);
        Integer updatedFaulty = currentStatus.get(2);

        Integer changeStatusValue = changeStatusDescriptor.getQuantity();
        switch (changeStatusDescriptor.getInitialStatus()) {
        case "Ready":
            updatedReady -= changeStatusValue;
            break;
        case "On_Loan":
            updatedOnLoan -= changeStatusValue;
            break;
        case "Faulty":
            updatedFaulty -= changeStatusValue;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_STATUS_FIELD);
        }

        switch (changeStatusDescriptor.getUpdatedStatus()) {
        case "Ready":
            updatedReady += changeStatusValue;
            break;
        case "On_Loan":
            updatedOnLoan += changeStatusValue;
            break;
        case "Faulty":
            updatedFaulty += changeStatusValue;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_STATUS_FIELD);
        }
        updatedStatus.add(updatedReady);
        updatedStatus.add(updatedOnLoan);
        updatedStatus.add(updatedFaulty);

        return new Item(itemToUpdate.getName(), itemToUpdate.getQuantity(), itemToUpdate.getMinQuantity(),
                updatedStatus, itemToUpdate.getTags());
    }
    /**
     * Stores the details to update the item with.
     */
    public static class ChangeStatusDescriptor {
        private Integer changeStatusQuantity;
        private String initialStatus;
        private String updatedStatus;

        public ChangeStatusDescriptor() {}
        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public ChangeStatusDescriptor (ChangeStatusDescriptor toCopy) {
            setQuantity(toCopy.changeStatusQuantity);
            setInitialStatus(toCopy.initialStatus);
            setUpdatedStatus(toCopy.updatedStatus);
        }

        public void setQuantity(Integer changeStatusQuantity) {
            this.changeStatusQuantity = changeStatusQuantity;
        }
        public Integer getQuantity() {
            return changeStatusQuantity;
        }
        public void setInitialStatus(String initialStatus) {
            this.initialStatus = initialStatus;
        }
        public String getInitialStatus() {
            return initialStatus;
        }

        public void setUpdatedStatus(String updatedStatus) {
            this.updatedStatus = updatedStatus;
        }
        public String getUpdatedStatus() {
            return updatedStatus;
        }

    }
}
