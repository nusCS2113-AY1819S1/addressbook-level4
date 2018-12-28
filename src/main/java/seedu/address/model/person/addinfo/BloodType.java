package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

//@@author chokxy
/**
 * Represents a Person's Blood Type in the address book.
 */

public class BloodType {
    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS =
            "Blood type should be A, B, O or AB followed by - or +";
    public static final String TYPE_VALIDATION_REGEX = "[ABO]*" + "[-+]";

    private static final String[] incorrectBloodType = {"AO", "BO", "BA", "OA", "OB"};

    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param type A valid bloodtype.
     */
    public BloodType(String type) {
        requireNonNull(type);
        value = type;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }

    public static boolean isIncorrectBloodType(String test) {
        return Arrays.stream(incorrectBloodType).parallel().anyMatch(test::contains);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof BloodType
                && value.equals(((BloodType) other).value));

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
