package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.ui.LoginDialogBoxUserIdPassword;

public class UserId {

    public static final String MESSAGE_USERID_CONSTRAINTS =
            "User ID should only contain 9 alphanumeric characters, and it should not be blank and not have any spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERID_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullUserId;

    public UserId(String id) {
        id = LoginDialogBoxUserIdPassword.getUserId();
        requireNonNull(id);
        checkArgument(isValidUserId(id), MESSAGE_USERID_CONSTRAINTS);
        fullUserId = id;
    }

    /**
     * Returns true if a given string is a valid user ID.
     */
    public static boolean isValidUserId(String test) {
        return test.matches(USERID_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return fullUserId.hashCode();
    }
}
