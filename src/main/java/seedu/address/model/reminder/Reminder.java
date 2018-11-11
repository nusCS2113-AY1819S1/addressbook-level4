package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Time;
import seedu.address.model.todo.Title;

//@@author junweiljw
/**
 * Represents a Reminder.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {

    // Reminder description fields
    private final Title title;
    private final Date date;
    private final Time time;
    private final Agenda agenda;

    /**
     * Every field must be present and not null.
     */
    public Reminder(Title title, Date date, Time time, Agenda agenda) {
        requireAllNonNull(title, date, time, agenda);
        this.title = title;
        this.date = date;
        this.time = time;
        this.agenda = agenda;
    }

    public Title getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * Returns true if both reminders of the same title have time that is the same.
     * This prevents creating a reminder for meetings occurring at the same time.
     */
    public boolean isSameReminder(Reminder otherTime) {
        if (otherTime == this) {
            return true;
        }

        return otherTime != null
                && otherTime.getTitle().equals(getTitle())
                && (otherTime.getDate().equals(getDate()))
                && (otherTime.getTime().equals(getTime()))
                && (otherTime.getAgenda().equals(getAgenda()));
    }

    /**
     * Returns true if two reminders have the same fields
     * Represents a strong notion of equality
     */
    public boolean equals(Reminder other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder != null
                && otherReminder.getTitle().equals(getTitle())
                && (otherReminder.getDate().equals(getDate()))
                && (otherReminder.getTime().equals(getTime()))
                && (otherReminder.getAgenda().equals(getAgenda()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, date, time, agenda);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Title: ")
                .append(getTitle())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime())
                .append(" Agenda: ")
                .append(getAgenda());
        return builder.toString();
    }
}
