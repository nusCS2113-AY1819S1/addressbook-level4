package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.ExpenseCategory;
import seedu.address.model.expense.ExpenseDate;
import seedu.address.model.expense.ExpenseValue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditExpenseDescriptor objects.
 */
public class EditExpenseDescriptorBuilder {

    private EditExpenseDescriptor descriptor;

    public EditExpenseDescriptorBuilder() {
        descriptor = new EditExpenseDescriptor();
    }

    public EditExpenseDescriptorBuilder(EditExpenseDescriptor descriptor) {
        this.descriptor = new EditExpenseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditExpenseDescriptor} with fields containing {@code expense}'s details
     */
    public EditExpenseDescriptorBuilder(Expense expense) {
        descriptor = new EditExpenseDescriptor();
        descriptor.setExpenseCategory(expense.getExpenseCategory());
        descriptor.setExpenseDate(expense.getExpenseDate());
        descriptor.setExpenseValue(expense.getExpenseValue());
        descriptor.setTags(expense.getTags());
    }

    /**
     * Sets the {@code ExpenseCategory} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withExpenseCategory(String expenseCategory) {
        descriptor.setExpenseCategory(new ExpenseCategory(expenseCategory));
        return this;
    }

    /**
     * Sets the {@code ExpenseDate} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withExpenseDate(String expenseDate) {
        descriptor.setExpenseDate(new ExpenseDate(expenseDate));
        return this;
    }

    /**
     * Sets the {@code ExpenseValue} of the {@code EditExpenseDescriptor} that we are building.
     */
    public EditExpenseDescriptorBuilder withExpenseValue(String expenseValue) {
        descriptor.setExpenseValue(new ExpenseValue(expenseValue));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditExpenseDescriptor}
     * that we are building.
     */
    public EditExpenseDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditExpenseDescriptor build() {
        return descriptor;
    }
}
