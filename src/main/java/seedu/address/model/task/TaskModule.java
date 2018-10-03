package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's module in the to-do list.
 * Guarantees: immutable; is valid as declared in {@link #isValidModule(String)}
 */
public class TaskModule {

    public static final String MESSAGE_MODULE_CONSTRAINTS =
            "Module can take any values, and it should not be blank";

    /*
     * The first character of the module must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MODULE_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code TaskModule}.
     *
     * @param module A valid module.
     */
    public TaskModule(String module) {
        requireNonNull(module);
        checkArgument(isValidModule(module), MESSAGE_MODULE_CONSTRAINTS);
        value = module;
    }

    /**
     * Returns true if a given string is a valid module.
     */
    public static boolean isValidModule(String test) {
        return test.matches(MODULE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskModule // instanceof handles nulls
                && value.equals(((TaskModule) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
