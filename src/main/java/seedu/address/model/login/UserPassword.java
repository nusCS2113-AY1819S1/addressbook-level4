package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an account's user password in the login book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserPassword(String)}
 */
public class UserPassword {
    public static final String MESSAGE_USERPASSWORD_CONSTRAINTS = "There must be a pa/ prefix before the password. "
            + "User password is case-sensitive, and it should not be blank and not have any spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERPASSWORD_VALIDATION_REGEX = "[\\S]*";

    public final String fullUserPassword;

    public UserPassword(String pass) {
        requireNonNull(pass);
        checkArgument(isValidUserPassword(pass), MESSAGE_USERPASSWORD_CONSTRAINTS);
        fullUserPassword = pass;
    }

    /**
     * Returns true if a given string is a valid user ID.
     */
    public static boolean isValidUserPassword(String test) {
        return test.matches(USERPASSWORD_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return fullUserPassword.hashCode();
    }
}
