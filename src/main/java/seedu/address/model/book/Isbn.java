package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Book's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Isbn {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Isbn numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String PHONE_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Isbn}.
     *
     * @param phone A valid phone number.
     */
    public Isbn(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_PHONE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Isbn // instanceof handles nulls
                && value.equals(((Isbn) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
