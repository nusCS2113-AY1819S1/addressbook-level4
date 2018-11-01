package seedu.address.model.task;

/**
 * Represents a Task's datails in the task list .
 * Guarantees: immutable.
 */
public class Body {

    public static final String MESSAGE_BODY_CONSTRAINTS =
            "Task body can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BODY_VALIDATION_REGEX = "[^\\s].*";

    public final String bodyString;

    public Body(String bodyString) {
        this.bodyString = bodyString;
    }

    @Override
    public String toString() {
        return bodyString;
    }

    /**
     * Returns true if a given string is a valid body.
     */
    public static boolean isValidBody(String test) {
        return test.matches(BODY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Body // instanceof handles nulls
                && bodyString.equals(((Body) other).bodyString)); // state check
    }

    @Override
    public int hashCode() {
        return bodyString.hashCode();
    }
}
