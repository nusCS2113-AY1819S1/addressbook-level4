package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username only accepts alphanumeric characters, not empty and no spaces";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String username;

    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), USERNAME_VALIDATION_REGEX);
        this.username = username;
    }

    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX) && !test.equals("");
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //
                || (other instanceof Username
                && this.username.equals(((Username) other).username));
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
