package seedu.address.model.task;

/**
 * Represents a Task's priority in the task list.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Task priority can only be 'HIGH', 'MED' or 'LOW' (case-insensitive), and it should not be blank";
    public static final String PRIORITY_VALIDATION_REGEX = "HIGH|high|MED|med|LOW|low";

    public final String priorityString;

    public Priority(String priorityString) {
        this.priorityString = priorityString;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priorityString;
    }
}
