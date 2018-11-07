package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

import seedu.address.commons.util.TimeUtil;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final TaskName name;
    private final TaskModule module;

    // Data fields
    private final TaskDate date;
    private final TaskPriority priority;

    private boolean isComplete;

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, TaskModule module, TaskDate date, TaskPriority priority, boolean isComplete) {
        requireAllNonNull(name, module, date, priority);
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
        this.isComplete = isComplete;
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

    public boolean getComplete() {
        return isComplete;
    }

    public void setAsCompleted() {
        isComplete = true;
    }

    public void setAsUncompleted() {
        isComplete = false;
    }

    public String getMonth() {
        return date.toString().substring(3, 5);
    }

    public String getDay() {
        return date.toString().substring(0, 2);
    }

    public String getNameString() {
        return name.toString().toLowerCase();
    }

    public Date getDayInTypeDate() {
        TimeUtil timeUtil = new TimeUtil();
        String date = timeUtil.dateToStringConverter(new Date());
        String currentYear = date.substring(6, 10);
        return timeUtil.stringToDateConverter( getDay() + '/' + getMonth() + '/' + currentYear);
    }

    /**
     * Returns true if both tasks are totally the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName())
                && otherTask.getModule().equals(getModule());
    }

    /**
     * return -1 for deadline has passed
     * return 0 for deadline is not urgent (not in the following 7 days)
     * return 1 for "urgent" deadline (within the following 7 days)
     */
    public int notification() {
        TimeUtil timeUtil = new TimeUtil();
        int interval = timeUtil.getDayInterval(timeUtil.getCurrentDate(), getDayInTypeDate());
        if (interval < 0) {
            return -1;
        }
        else if (interval > 7) {
            return 0;
        }
        else {
            return 1;
        }
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
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
        return otherTask.getName().equals(getName())
                && otherTask.getModule().equals(getModule())
                && otherTask.getDate().equals(getDate())
                && otherTask.getPriority().equals(getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, module, date, priority, isComplete);
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
