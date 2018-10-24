package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EXPENSES;

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
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing expense in the expense book.
 */
public class EditExpenseCommand extends Command {
    public static final String COMMAND_WORD = "editExpense";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the expense identified "
            + "by the index number used in the displayed expense list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EXPENSE_CATEGORY + "EXPENSE CATEGORY] "
            + "[" + PREFIX_EXPENSE_DATE + "EXPENSE DATE] "
            + "[" + PREFIX_EXPENSE_VALUE + "EXPENSE VALUE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPENSE_CATEGORY + "Food "
            + PREFIX_EXPENSE_VALUE + "11.11";
    public static final String MESSAGE_EDIT_EXPENSE_SUCCESS = "Expense edited successfully";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditExpenseDescriptor editExpenseDescriptor;

    /**
     * @param index of the expense in the filtered expense list to edit
     * @param editExpenseDescriptor details to edit the expense with
     */
    public EditExpenseCommand(Index index, EditExpenseDescriptor editExpenseDescriptor) {
        requireNonNull(index);
        requireNonNull(editExpenseDescriptor);

        this.index = index;
        this.editExpenseDescriptor = new EditExpenseDescriptor(editExpenseDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Expense> lastShownList = model.getFilteredExpenseList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
        }

        Expense expenseToEdit = lastShownList.get(index.getZeroBased());
        Expense editedExpense = createEditedExpense(expenseToEdit, editExpenseDescriptor);

        model.updateExpense(expenseToEdit, editedExpense);
        model.updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
        model.commitExpenseBook();
        return new CommandResult(String.format(MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense));
    }

    /**
     * Creates and returns a {@code Expense} with the details of {@code expenseToEdit}
     * edited with {@code editExpenseDescriptor}.
     */
    private static Expense createEditedExpense(Expense expenseToEdit, EditExpenseDescriptor editExpenseDescriptor) {
        assert expenseToEdit != null;

        ExpenseCategory updatedExpenseCategory = editExpenseDescriptor.getExpenseCategory()
                .orElse(expenseToEdit.getExpenseCategory());
        ExpenseDate updatedExpenseDate = editExpenseDescriptor.getExpenseDate()
                .orElse(expenseToEdit.getExpenseDate());
        ExpenseValue updatedExpenseValue = editExpenseDescriptor.getExpenseValue()
                .orElse(expenseToEdit.getExpenseValue());
        Set<Tag> updatedTags = editExpenseDescriptor.getTags().orElse(expenseToEdit.getTags());

        return new Expense(updatedExpenseCategory, updatedExpenseDate, updatedExpenseValue, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseCommand)) {
            return false;
        }

        // state check
        EditExpenseCommand e = (EditExpenseCommand) other;
        return index.equals(e.index)
                && editExpenseDescriptor.equals(e.editExpenseDescriptor);
    }

    /**
     * Stores the details to edit the expense with. Each non-empty field value will replace the
     * corresponding field value of the expense.
     */
    public static class EditExpenseDescriptor {
        private ExpenseCategory expenseCategory;
        private ExpenseDate expenseDate;
        private ExpenseValue expenseValue;
        private Set<Tag> tags;

        public EditExpenseDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
            setExpenseCategory(toCopy.expenseCategory);
            setExpenseDate(toCopy.expenseDate);
            setExpenseValue(toCopy.expenseValue);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(expenseCategory, expenseDate, expenseValue, tags);
        }

        public void setExpenseCategory(ExpenseCategory expenseCategory) {
            this.expenseCategory = expenseCategory;
        }

        public Optional<ExpenseCategory> getExpenseCategory() {
            return Optional.ofNullable(expenseCategory);
        }

        public void setExpenseDate(ExpenseDate expenseDate) {
            this.expenseDate = expenseDate;
        }

        public Optional<ExpenseDate> getExpenseDate() {
            return Optional.ofNullable(expenseDate);
        }

        public void setExpenseValue(ExpenseValue expenseValue) {
            this.expenseValue = expenseValue;
        }

        public Optional<ExpenseValue> getExpenseValue() {
            return Optional.ofNullable(expenseValue);
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
            if (!(other instanceof EditExpenseDescriptor)) {
                return false;
            }

            // state check
            EditExpenseDescriptor e = (EditExpenseDescriptor) other;

            return getExpenseCategory().equals(e.getExpenseCategory())
                    && getExpenseDate().equals(e.getExpenseDate())
                    && getExpenseValue().equals(e.getExpenseValue())
                    && getTags().equals(e.getTags());
        }
    }

}
