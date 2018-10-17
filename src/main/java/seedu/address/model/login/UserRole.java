package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an account's user role in the login book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserRole(String)}
 */
public class UserRole {

    public static final String MESSAGE_USERROLE_CONSTRAINTS = "User role must not contain any digits or special"
            + " characters, and it should not be blank and not have any spaces.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERROLE_VALIDATION_REGEX = "[a-zA-Z]+";

    public final String fullUserRole;

    /**
     * Constructs a {@code UserRole}.
     *
     * @param role A valid userrole.
     */
    public UserRole(String role) {
        requireNonNull(role);
        checkArgument(isValidUserRole(role), MESSAGE_USERROLE_CONSTRAINTS);
        fullUserRole = role;
    }

    /**
     * Returns true if a given string is a valid user role.
     */
    public static boolean isValidUserRole(String test) {
        return test.matches(USERROLE_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return fullUserRole.hashCode();
    }
}
