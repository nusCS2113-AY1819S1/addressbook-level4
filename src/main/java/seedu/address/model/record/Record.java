package seedu.address.model.record;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Record in the expense book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final Name name;
    private final Date date;
    private final Income income;

    // Data fields
    private final Expense expense;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Date date, Income income, Expense expense, Set<Tag> tags) {
        requireAllNonNull(name, date, income, expense, tags);
        this.name = name;
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Income getIncome() {
        return income;
    }

    public Expense getExpense() {
        return expense;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both records of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two records.
     */
    public boolean isSameRecord(Record otherRecord) {
        if (otherRecord == this) {
            return true;
        }

        return otherRecord != null
                && otherRecord.getName().equals(getName())
                && (otherRecord.getDate().equals(getDate()) || otherRecord.getIncome().equals(getIncome()));
    }

    /**
     * Returns true if both records have the same identity and data fields.
     * This defines a stronger notion of equality between two records.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;
        return otherRecord.getName().equals(getName())
                && otherRecord.getDate().equals(getDate())
                && otherRecord.getIncome().equals(getIncome())
                && otherRecord.getExpense().equals(getExpense())
                && otherRecord.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, income, expense, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Day : ")
                .append(getDate())
                .append(" Income: ")
                .append(getIncome())
                .append(" Expense: ")
                .append(getExpense())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
