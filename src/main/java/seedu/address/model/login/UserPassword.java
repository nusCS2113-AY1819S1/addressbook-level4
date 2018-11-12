package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import seedu.address.logic.LoginManager;

/**
 * Represents an account's user password in the login book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserPassword(String)}
 */
public class UserPassword {
    public static final String MESSAGE_USERPASSWORD_CONSTRAINTS = "User password is case-sensitive, and it "
            + "should not be blank and not have any spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERPASSWORD_VALIDATION_REGEX = "[\\S]*";

    public final String fullUserPassword;

    /**
     * Constructs a {@code UserPassword}.
     *
     * @param pass A valid user password.
     */
    public UserPassword(String pass) throws UnsupportedEncodingException {
        requireNonNull(pass);
        if (LoginManager.getIsCurrentlyCreatingAccount() && !LoginManager.getIsCurrentlyTesting()) {
            checkArgument(isValidUserPassword(pass), MESSAGE_USERPASSWORD_CONSTRAINTS);
        }
        if (LoginManager.getIsCurrentlyCreatingAccount()) {
            fullUserPassword = Base64.getEncoder().encodeToString(pass.getBytes("utf-8"));
        } else {
            fullUserPassword = pass;
        }
    }

    /**
     * Returns true if a given string is a valid user password.
     */
    public static boolean isValidUserPassword(String test) {
        return test.matches(USERPASSWORD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserPassword // instanceof handles nulls
                && fullUserPassword.equals(((UserPassword) other).fullUserPassword)); // state check
    }

    @Override
    public int hashCode() {
        return fullUserPassword.hashCode();
    }
}
