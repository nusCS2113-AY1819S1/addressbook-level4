package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Data fields
    private final TaskName name;
    private final TaskModule module;
    private final TaskDate date;
    private final TaskPriority priority;
    private boolean completeness;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, TaskModule module, TaskDate date, TaskPriority priority) {
        requireAllNonNull(name, module, date, priority);
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
        completeness = false;
    }

    public TaskName getName() {
        return name;
    }

    public TaskModule getModule() {
        return module;
    }

    public TaskDate getDate() {
        return date;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public boolean getCompleteness() {return completeness;}

    public void setAsCompleted() {completeness = true;}

    /**
     * Returns true if both tasks are totally the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherPerson = (Task) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getModule().equals(getModule())
                && otherPerson.getDate().equals(getDate())
                && otherPerson.getPriority().equals(getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, module, date, priority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module: ")
                .append(getModule())
                .append(" Date: ")
                .append(getDate())
                .append(" Priority: ")
                .append(getPriority());
        return builder.toString();
    }

}
