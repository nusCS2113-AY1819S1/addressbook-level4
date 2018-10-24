package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Expense in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expense implements Comparable<Expense> {

    private final ExpenseCategory expenseCategory;
    private final ExpenseDate expenseDate;
    private final ExpenseValue expenseValue;

    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Expense(ExpenseCategory expenseCategory, ExpenseDate expenseDate, ExpenseValue expenseValue, Set<Tag> tags) {
        requireAllNonNull(expenseCategory, expenseValue, expenseDate, tags);
        this.expenseCategory = expenseCategory;
        this.expenseDate = expenseDate;
        this.expenseValue = expenseValue;
        this.tags.addAll(tags);
    }

    public ExpenseCategory getExpenseCategory() {
        return this.expenseCategory;
    }

    public ExpenseDate getExpenseDate() {
        return this.expenseDate;
    }

    public ExpenseValue getExpenseValue() {
        return this.expenseValue;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(expenseCategory, expenseDate, expenseValue, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Category: ")
                .append(getExpenseCategory())
                .append(" $")
                .append(getExpenseValue())
                .append(" spent on: ")
                .append(getExpenseDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int compareTo(Expense expense) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = simpleDateFormat.parse(this.getExpenseDate().toString());
            Date date2 = simpleDateFormat.parse(expense.getExpenseDate().toString());
            return date2.compareTo(date1);
        } catch (ParseException pe) {
            return 0;
        }
    }
}
