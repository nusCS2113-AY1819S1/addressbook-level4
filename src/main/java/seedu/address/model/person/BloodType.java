package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Blood Type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */

public class BloodType {
    public static final String BLOODTYPE_CONSTRAINTS =
            "Blood type should be A, B, O or AB followed by - or +";
    public static final String TYPE_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param Type A valid bloodtype.
     */
    public BloodType(String Type) {
        requireNonNull(Type);
        checkArgument(isValidType(Type), BLOODTYPE_CONSTRAINTS);
        value = Type;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
