package seedu.planner.model.record;

import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.planner.model.tag.Tag;

/**
 * Represents a Record in the financial planner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity fields
    private final Name name;

    // Data fields
    private final Date date;
    private final MoneyFlow moneyFlow;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Record(Name name, Date date, MoneyFlow moneyFlow, Set<Tag> tags) {
        requireAllNonNull(name, date, moneyFlow, tags);
        this.name = name;
        this.date = date;
        this.moneyFlow = moneyFlow;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public MoneyFlow getMoneyFlow() {
        return moneyFlow;
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
                && (otherRecord.getDate().equals(getDate()) || otherRecord.getMoneyFlow().equals(getMoneyFlow()));
    }

    /**
     * Return whether the record has the same date requried.
     */
    public boolean isSameDateRecord (Date date) {
        return date.getDay() == this.date.getDay()
                && date.getMonth() == this.date.getMonth()
                && date.getYear() == this.date.getYear();
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
                && otherRecord.getMoneyFlow().equals(getMoneyFlow())
                && otherRecord.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, moneyFlow, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" MoneyFlow:")
                .append(getMoneyFlow())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
