package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import seedu.address.logic.LoginManager;

/**
 * Represents an account's user ID in the login book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUserId(String)}
 */
public class UserId {

    public static final String MESSAGE_USERID_CONSTRAINTS = "User ID should only contain 9 alphanumeric characters, "
                    + " with the first and last character, upper-case only alphabets, "
            + "7 characters in between, all integers, and it should not be blank and not have any spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERID_VALIDATION_REGEX = "[A-Z][0-9]{7}[A-Z]";

    public final String fullUserId;

    /**
     * Constructs a {@code UserId}.
     *
     * @param id A valid userid.
     */
    public UserId(String id) throws UnsupportedEncodingException {
        requireNonNull(id);
        if (LoginManager.getIsCurrentlyCreatingAccount() && !LoginManager.getIsCurrentlyTesting()) {
            checkArgument(isValidUserId(id), MESSAGE_USERID_CONSTRAINTS);
        }
        if (LoginManager.getIsCurrentlyCreatingAccount()) {
            fullUserId = Base64.getEncoder().encodeToString(id.getBytes("utf-8"));
        } else {
            fullUserId = id;
        }

    }

    /**
     * Returns true if a given string is a valid user ID.
     */
    public static boolean isValidUserId(String test) {
        return test.matches(USERID_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserId // instanceof handles nulls
                && fullUserId.equals(((UserId) other).fullUserId)); // state check
    }

    @Override
    public int hashCode() {
        return fullUserId.hashCode();
    }
}
