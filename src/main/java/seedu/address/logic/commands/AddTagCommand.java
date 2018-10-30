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
 * Add the given tags to selected item by index.
 */

public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addTag";

    public static final String MESSAGE_SUCCESS = "Added tags to the selected item.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add the inputted tags to the selected item "
            + "by the index number used in the displayed item list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG [MORE_TAGS]"
            + " Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + " Lab1";

    private final AddTagDescriptor addTagDescriptor;
    private final Index index;

    public AddTagCommand(Index index, AddTagDescriptor addTagDescriptor) {
        requireNonNull(index);
        requireNonNull(addTagDescriptor);

        this.index = index;
        this.addTagDescriptor = new AddTagDescriptor(addTagDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, addTagDescriptor);

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitStockList();
        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code addTagDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, AddTagCommand.AddTagDescriptor addTagDescriptor) {
        assert itemToEdit != null;

        Name updatedName = itemToEdit.getName();
        Quantity updatedQuantity = itemToEdit.getQuantity();
        Quantity updatedMinQuantity = itemToEdit.getMinQuantity();
        Set<Tag> updatedTags = addTagDescriptor.getTags();
        updatedTags.addAll(itemToEdit.getTags());
        return new Item(updatedName, updatedQuantity, updatedMinQuantity, updatedTags);
    }

    /**
     * Temporarily stores the tags to be added.
     */
    public static class AddTagDescriptor {
        private Set<Tag> tags;

        public AddTagDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddTagDescriptor(AddTagDescriptor toCopy) {
            setTags(toCopy.tags);
        }

        /**
         * Returns true if there is tag to add.
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
            if (!(other instanceof AddTagDescriptor)) {
                return false;
            }

            // state check
            AddTagDescriptor e = (AddTagDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
