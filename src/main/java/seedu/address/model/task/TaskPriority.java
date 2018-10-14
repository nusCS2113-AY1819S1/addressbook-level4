package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's priority in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class TaskPriority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority should only contain one number: 1, 2 or 3";
    public static final String PRIORITY_VALIDATION_REGEX = "[123]";

    public final String value;

    /**
     * Constructs a {@code TaskPriority}.
     *
     * @param priority A valid priority.
     */
    public TaskPriority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_PRIORITY_CONSTRAINTS);
        value = priority;
    }

    /**
     * Returns true if a given string is a valid priority number.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskPriority // instanceof handles nulls
                && value.equals(((TaskPriority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
