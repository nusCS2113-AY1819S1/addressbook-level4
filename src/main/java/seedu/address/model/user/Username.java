package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a user's username credential.
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Usernames should adhere to the following constraints:\n"
            + "1. Begin and end with alphanumeric characters.\n"
            + "2. Be at least 2 characters long.\n"
            + "3. Special characters can only include underscores and fullstops.";
    private static final String FIRST_CHARACTER_REGEX = "[^\\W_]";  // alphanumeric except underscores
    private static final String MIDDLE_REGEX = "[a-zA-Z0-9.-_-]*";   // alphanumeric, period, hyphen, underscore
    private static final String LAST_CHARACTER_REGEX = "[^\\W_]";
    public static final String USERNAME_VALIDATION_REGEX = FIRST_CHARACTER_REGEX +
            MIDDLE_REGEX + LAST_CHARACTER_REGEX;

    public final String value;

    /**
     * Constructs an {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        value = username;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
