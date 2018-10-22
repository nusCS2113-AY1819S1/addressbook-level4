package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {
    private static final String PLACEHOLDER_DEADLINE = "1/1";
    private String deadline;
    private final String title;
    private final String description;
    private final PriorityLevel priorityLevel;
    private boolean isCompleted;

    public Task(String deadline, String title, String description, PriorityLevel priorityLevel) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.isCompleted = false;
    }

    public Task(String deadline, String title, String description, PriorityLevel priorityLevel, boolean isCompleted) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.isCompleted = isCompleted;
    }

    public Task(String title, String description, PriorityLevel priorityLevel) {
        this.deadline = PLACEHOLDER_DEADLINE;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.isCompleted = false;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Marks the task as completed by
     * setting @code {isCompleted} to true
     */
    public Task completed() {
        this.isCompleted = true;
        return this;
    }

    /**
     * Returns true if both tasks have the same deadline and title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getTitle().equals(getTitle());
    }

    /**
     * Defers the task to a later
     * @param deadline
     * @return the new Task
     */
    public Task deferred(String deadline) {
        this.deadline = deadline;
        return this;
    }
    /**
     * Returns true if both tasks have the same data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTitle().equals(getTitle())
                && otherTask.getDeadline().equals(getTitle())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getPriorityLevel().equals(getPriorityLevel())
                && otherTask.isCompleted() == isCompleted();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(deadline, title, description, priorityLevel, isCompleted);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDeadline())
                .append(" | ")
                .append(getTitle())
                .append(" : ")
                .append(getDescription())
                .append(" Priority: ")
                .append(getPriorityLevel());
        /*if (isCompleted) {
            builder.append(" => Completed!");
        } else {
            builder.append(" => Not completed!");
        }*/
        return builder.toString();
    }
}
