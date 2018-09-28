package seedu.address.model.tag;

import seedu.address.model.record.Date;
import seedu.address.model.record.MoneyFlow;
import seedu.address.model.record.Name;
import seedu.address.model.record.Record;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TagData {

    private final Name name;
    private final Date date;
    private final MoneyFlow moneyFlow;

    public TagData(Name name, Date date, MoneyFlow moneyFlow) {
        requireAllNonNull(name, date, moneyFlow);
        this.name = name;
        this.date = date;
        this.moneyFlow = moneyFlow;
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

    public boolean isSameTag(TagData otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null
                && otherTag.getName().equals(getName())
                && (otherTag.getDate().equals(getDate()) || otherTag.getMoneyFlow().equals(getMoneyFlow()));
    }

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
                && otherRecord.getMoneyFlow().equals(getMoneyFlow());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, moneyFlow);
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
        return builder.toString();
    }

}
