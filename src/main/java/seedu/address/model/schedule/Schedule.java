package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.EmployeeId;

/**
 * Represents a schedule list.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    // Data fields
    private final EmployeeId id;
    private final Type type;
    private final Date date;

    public Schedule (EmployeeId id, Type type, Date date) {
        requireAllNonNull(id, type, date);
        this.type = type;
        this.date = date;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Date getScheduleDate() {
        return date;
    }

    public EmployeeId getEmployeeId() {
        return id;
    }

    public String getScheduleYear() {
        String year;
        String[] date = getScheduleDate().toString().split("/");
        year = date[2];
        return year;
    }

    /**
     * Returns true if both schedules have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return otherSchedule.getScheduleDate().equals(getScheduleDate())
                && otherSchedule.getType().equals(getType())
                && otherSchedule.getEmployeeId().equals(getEmployeeId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, date);
    }

    /**
     * Returns true if both schedules identity fields are the same.
     */
    public boolean isSameSchedule(Schedule otherSchedule) {
        if (otherSchedule == this) {
            return true;
        }

        return otherSchedule != null
                && otherSchedule.getEmployeeId().equals(getEmployeeId())
                && (otherSchedule.getType().equals(getType())
                && otherSchedule.getScheduleDate().equals(getScheduleDate()));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getScheduleDate())
                .append(" Type: ")
                .append(getType());
        return builder.toString();
    }

}
