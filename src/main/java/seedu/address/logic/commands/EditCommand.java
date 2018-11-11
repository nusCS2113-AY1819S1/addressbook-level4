package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_COST_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_DEFAULT_SELLING_PRICE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.user.manager.ManagerModel;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the drink identified "
            + "by the index number used in the displayed drink list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DRINK_COST_PRICE + "COST_PRICE] "
            + "[" + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "SELLING_PRICE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DRINK_COST_PRICE + "5.00 "
            + PREFIX_DRINK_DEFAULT_SELLING_PRICE + "10.00";

    public static final String MESSAGE_EDIT_DRINK_SUCCESS = "Edited Drink: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditDrinkDescriptor editDrinkDescriptor;

    /**
     * @param index               of the person in the filtered drink list to edit
     * @param editDrinkDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditDrinkDescriptor editDrinkDescriptor) {
        requireAllNonNull(index, editDrinkDescriptor);

        this.index = index;
        this.editDrinkDescriptor = editDrinkDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assert model instanceof ManagerModel;
        ManagerModel managerModel = (ManagerModel) model;

        List<Drink> lastShownList = model.getFilteredDrinkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DRINK_DISPLAYED_INDEX);
        }

        Drink drinkToEdit = lastShownList.get(index.getZeroBased());

        if (editDrinkDescriptor.getSellingPrice().isPresent()) {
            managerModel.updateSellingPrice(drinkToEdit, editDrinkDescriptor.getSellingPrice().get());
        }

        if (editDrinkDescriptor.getCostPrice().isPresent()) {
            managerModel.updateCostPrice(drinkToEdit, editDrinkDescriptor.getCostPrice().get());
        }

        if (editDrinkDescriptor.getTags().isPresent()) {
            managerModel.updateTags(drinkToEdit, editDrinkDescriptor.getTags().get());
        }

        model.updateFilteredDrinkList(Model.PREDICATE_SHOW_ALL_DRINKS);

        return new CommandResult(String.format(MESSAGE_EDIT_DRINK_SUCCESS, drinkToEdit));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index);
    }


    /**
     * Stores the details to edit the drink with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditDrinkDescriptor {
        private Price costPrice;
        private Price sellingPrice;
        private Set<Tag> tags;

        public EditDrinkDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDrinkDescriptor(EditDrinkDescriptor toCopy) {
            setCostPrice(toCopy.costPrice);
            setSellingPrice(toCopy.sellingPrice);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(costPrice, sellingPrice, tags);
        }

        public void setCostPrice(Price costPrice) {
            this.costPrice = costPrice;
        }

        public Optional<Price> getCostPrice() {
            return Optional.ofNullable(costPrice);
        }

        public void setSellingPrice(Price sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public Optional<Price> getSellingPrice() {
            return Optional.ofNullable(sellingPrice);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDrinkDescriptor)) {
                return false;
            }

            // state check
            EditDrinkDescriptor e = (EditDrinkDescriptor) other;

            return getCostPrice().equals(e.getCostPrice())
                    && getSellingPrice().equals(e.getSellingPrice())
                    && getTags().equals(e.getTags());
        }
    }
}
