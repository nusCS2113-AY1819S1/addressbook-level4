package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's PriorityLevel in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriorityLevel(String)}
 */
public class PriorityLevel {
    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority can only be of low, medium or high level";

    public final String priorityLevel;
    public final int priorityLevelInt;

    /**
     * Constructs a {@code PriorityLevel}.
     *
     * @param priority A valid priority.
     */
    public PriorityLevel(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriorityLevel(priority), MESSAGE_PRIORITY_CONSTRAINTS);
        priorityLevel = priority.toLowerCase();

        //@@author ChanChunCheong
        switch(priority) {
        case ("low"): {
            priorityLevelInt = 3;
            break;
        }
        case ("medium"): {
            priorityLevelInt = 2;
            break;
        }
        case ("high"): {
            priorityLevelInt = 1;
            break;
        }
        default:
            priorityLevelInt = 0;
        }
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPriorityLevel(String test) {
        String testInLowerCase = test.toLowerCase();
        if (testInLowerCase.equals("low") || testInLowerCase.equals("medium")
                || testInLowerCase.equals("high")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return priorityLevel;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriorityLevel // instanceof handles nulls
                && priorityLevel.equals(((PriorityLevel) other).priorityLevel)); // state check
    }

    @Override
    public int hashCode() {
        return priorityLevel.hashCode();
    }
}
