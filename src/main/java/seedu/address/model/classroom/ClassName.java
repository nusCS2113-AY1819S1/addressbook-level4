package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a classname.
 */
public class ClassName {
    public static final String MESSAGE_CLASSNAME_CONSTRAINTS =
            "Class Names should only contain between 1 to 3 alphanumeric characters and it should not be blank.";
    private static final String MESSAGE_CLASSNAME_REGEX = "^[a-zA-Z0-9]{1,3}$";
    private String value;

    public ClassName(String className) {
        requireNonNull(className);
        checkArgument(isValidClassName(className), MESSAGE_CLASSNAME_CONSTRAINTS);
        value = className;
    }

    public static Boolean isValidClassName(String test) {
        return test.matches(MESSAGE_CLASSNAME_REGEX);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassName // instanceof handles nulls
                && value.equals(((ClassName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
