//@@author gaoqikai
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.item.Quantity;
import seedu.address.model.tag.Tag;




/**
 * Delete the given tags to selected item by index.
 */

public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "deleteTag";

    public static final String MESSAGE_SUCCESS = "Deleted tags from the selected item.";

    public static final String MESSAGE_NO_TAG = "Please include the tags you want to delete.";

    public static final String MESSAGE_DOES_NOT_EXIST = "The selected item does not contain the inputted tags.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete the inputted tags from the selected item "
            + "by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG [MORE_TAGS]"
            + " Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + " Lab1";

    private static boolean flag = false; //to indicate whether a deletion is executed.
    private final DeleteTagDescriptor deleteTagDescriptor;
    private final Index index;

    public DeleteTagCommand(Index index, DeleteTagDescriptor deleteTagDescriptor) {
        requireNonNull(index);
        requireNonNull(deleteTagDescriptor);

        this.index = index;
        this.deleteTagDescriptor = new DeleteTagDescriptor(deleteTagDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, deleteTagDescriptor);

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(flag ? (MESSAGE_SUCCESS) : (MESSAGE_DOES_NOT_EXIST));

    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code deleteTagDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, DeleteTagCommand.DeleteTagDescriptor deleteTagDescriptor) {
        assert itemToEdit != null;

        Name updatedName = itemToEdit.getName();
        Quantity updatedQuantity = itemToEdit.getQuantity();
        Quantity updatedMinQuantity = itemToEdit.getMinQuantity();
        Set<Tag> updatedTags = new HashSet<>(itemToEdit.getTags());
        flag = updatedTags.removeIf((Tag current) -> deleteTagDescriptor.getTags().toString().toLowerCase()
                .contains(current.toString().toLowerCase()));
        return new Item(updatedName, updatedQuantity, updatedMinQuantity, updatedTags);
    }

    /**
     * Temporarily stores the tags to be added.
     */
    public static class DeleteTagDescriptor {
        private Set<Tag> tags;

        public DeleteTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public DeleteTagDescriptor(DeleteTagDescriptor toCopy) {
            setTags(toCopy.tags);
        }

        /**
         * Returns true if there is tag to delete.
         */
        public boolean haveTag() {
            return CollectionUtil.isAnyNonNull(tags);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteTagDescriptor)) {
                return false;
            }

            // state check
            DeleteTagDescriptor e = (DeleteTagDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
