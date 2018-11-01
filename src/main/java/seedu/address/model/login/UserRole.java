package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import seedu.address.logic.LoginManager;

/**
 * Represents an account's user role in the login book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserRole(String)}
 */
public class UserRole {

    public static final String MESSAGE_USERROLE_CONSTRAINTS = "User role must be either member, president "
            + "or treasurer.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERROLE_VALIDATION_REGEX = "\\b(member|president|treasurer)\\b";

    public final String fullUserRole;

    /**
     * Constructs a {@code UserRole}.
     *
     * @param role A valid userrole.
     */
    public UserRole(String role) throws UnsupportedEncodingException {
        requireNonNull(role);
        if (LoginManager.getIsCurrentlyCreatingAccount() && !LoginManager.getIsCurrentlyTesting()) {
            checkArgument(isValidUserRole(role), MESSAGE_USERROLE_CONSTRAINTS);
        }
        if (LoginManager.getIsCurrentlyCreatingAccount()) {
            fullUserRole = Base64.getEncoder().encodeToString(role.getBytes("utf-8"));
        } else {
            fullUserRole = role;
        }
    }

    /**
     * Returns true if a given string is a valid user role.
     */
    public static boolean isValidUserRole(String test) {
        return test.matches(USERROLE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserRole // instanceof handles nulls
                && fullUserRole.equals(((UserRole) other).fullUserRole)); // state check
    }

    @Override
    public int hashCode() {
        return fullUserRole.hashCode();
    }
}
