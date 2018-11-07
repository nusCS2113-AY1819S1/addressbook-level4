package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Used for Sort command.
 * Guarantees: is valid as declared in {@link #isValidParameter(String)}
 */
public class Parameter {

    public static final String MESSAGE_PARAMETER_CONSTRAINTS = "Sort Command Parameters must conform to the following "
            + "values: 'Sort'";

    private static final String PARAMETERS_REGEX = "SKILL"; // TODO expand with more parameters

    public final String value;

    /**
     * Constructs a {@code Parameter}.
     *
     * @parameter parameter A valid Parameter.
     */
    public Parameter(String parameter) {
        requireNonNull(parameter);
        checkArgument(isValidParameter(parameter), MESSAGE_PARAMETER_CONSTRAINTS);
        value = parameter;
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidParameter(String test) {
        return test.matches(PARAMETERS_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Parameter // instanceof handles nulls
                && value.equals(((Parameter) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
