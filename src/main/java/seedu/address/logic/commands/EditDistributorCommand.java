package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIST_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DISTRIBUTORS;

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
import seedu.address.model.distributor.Distributor;
import seedu.address.model.distributor.DistributorName;
import seedu.address.model.distributor.DistributorPhone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing distributor in the address book.
 */
public class EditDistributorCommand extends Command {

    public static final String COMMAND_WORD = "editdistributor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the distributor identified "
            + "by the index number used in the displayed distributor list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DIST_NAME + "DISTRIBUTOR NAME "
            + "[" + PREFIX_DIST_PHONE + "DISTRIBUTOR PHONE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DIST_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_DISTRIBUTOR_SUCCESS = "Edited Distributor: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_DISTRIBUTOR = "This distributor already exists in the address book.";
    private final Index index;
    private final EditDistributorDescriptor editDistributorDescriptor;
    /**
     * @param index of the product in the filtered distributor list to edit
     * @param editDistributorDescriptor details to edit the distributor with
     */
    public EditDistributorCommand(Index index, EditDistributorDescriptor editDistributorDescriptor) {
        requireNonNull(index);
        requireNonNull(editDistributorDescriptor);

        this.index = index;
        this.editDistributorDescriptor = new EditDistributorDescriptor(editDistributorDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Distributor> lastShownList = model.getFilteredDistributorList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
        }

        Distributor distributorToEdit = lastShownList.get(index.getZeroBased());
        Distributor editedDistributor = createEditedDistributor(distributorToEdit, editDistributorDescriptor);

        if (!distributorToEdit.isSameDistributor(editedDistributor) && model.hasDistributor(editedDistributor)) {
            throw new CommandException(MESSAGE_DUPLICATE_DISTRIBUTOR);
        }

        model.updateDistributor(distributorToEdit, editedDistributor);
        model.updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        model.commitDistributorBook();
        return new CommandResult(String.format(MESSAGE_EDIT_DISTRIBUTOR_SUCCESS, editedDistributor));
    }

    /**
     * Creates and returns a {@code Distributor} with the details of {@code distributorToEdit}
     * edited with {@code editDistributorDescriptor}.
     */
    private static Distributor createEditedDistributor(Distributor distributorToEdit,
                                                       EditDistributorDescriptor editDistributorDescriptor) {
        assert distributorToEdit != null;

        DistributorName updatedName = editDistributorDescriptor.getDistName().orElse(distributorToEdit.getDistName());
        DistributorPhone updatedPhone =
                editDistributorDescriptor.getDistPhone().orElse(distributorToEdit.getDistPhone());
        Set<Tag> updatedTags = editDistributorDescriptor.getTags().orElse(distributorToEdit.getTags());

        return new Distributor(updatedName, updatedPhone, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDistributorCommand)) {
            return false;
        }

        // state check
        EditDistributorCommand e = (EditDistributorCommand) other;
        return index.equals(e.index)
                && editDistributorDescriptor.equals(e.editDistributorDescriptor);
    }

    /**
     * Stores the details to edit the product with. Each non-empty field value will replace the
     * corresponding field value of the product.
     */
    public static class EditDistributorDescriptor {
        private DistributorName name;
        private DistributorPhone phone;
        private Set<Tag> tags;

        public EditDistributorDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDistributorDescriptor(EditDistributorDescriptor toCopy) {
            setDistName(toCopy.name);
            setDistPhone(toCopy.phone);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone);
        }

        public void setDistName(DistributorName name) {
            this.name = name;
        }

        public Optional<DistributorName> getDistName() {
            return Optional.ofNullable(name);
        }

        public void setDistPhone(DistributorPhone phone) {
            this.phone = phone;
        }

        public Optional<DistributorPhone> getDistPhone() {
            return Optional.ofNullable(phone);
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
            if (!(other instanceof EditDistributorDescriptor)) {
                return false;
            }

            // state check
            EditDistributorDescriptor e = (EditDistributorDescriptor) other;

            return getDistName().equals(e.getDistName())
                    && getDistPhone().equals(e.getDistPhone())
                    && getTags().equals(e.getTags());
        }
    }
}
