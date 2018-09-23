package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class UserPassword {

    public static final String MESSAGE_USERPASSWORD_CONSTRAINTS =
            "User password is case-sensitive, and it should not be blank and not have any spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERPASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullUserPassword;

    public UserPassword(String password) {
        requireNonNull(password);
        checkArgument(isValidUserPassword(password), MESSAGE_USERPASSWORD_CONSTRAINTS);
        fullUserPassword = password;
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
