package seedu.address.model.schedule;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Schedule's type in the address book.
 * Guarantees: immutable; is always valid
 */
public class Type {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "TYPE should only be WORK or LEAVE, case not sensitive and it should not be blank";

    /*
     * Type must either be WORK or LEAVE, exact match.
     */
    public static final String TYPE_VALIDATION_REGEX = "(^LEAVE$)|(^WORK$)";
    public static final String LEAVE = "LEAVE";
    public static final String WORK = "WORK";


    public final String value;
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type.toUpperCase()), MESSAGE_TYPE_CONSTRAINTS);
        value = type.toUpperCase();
    }
    @Override
    public String toString() {
        return value;
    }

    /**
     * Checks if the Schedule type is valid.
     * @param type to be tested with the regular expression.
     * @return Boolean, true if matches.
     */
    public static boolean isValidType(String type) {
        return type.toUpperCase().matches(TYPE_VALIDATION_REGEX);
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && value.equals(((Type) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
