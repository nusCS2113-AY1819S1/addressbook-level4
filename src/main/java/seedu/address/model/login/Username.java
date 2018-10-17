package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Username used to login to Inventarie Pro
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username only accepts alphanumeric characters, not empty and no spaces";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullUsername;

    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), USERNAME_VALIDATION_REGEX);
        fullUsername = username;
    }

    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX) && !test.equals("");
    }

    @Override
    public String toString() {
        return fullUsername;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //
                || (other instanceof Username
                && this.fullUsername.equals(((Username) other).fullUsername));
    }

    @Override
    public int hashCode() {
        return fullUsername.hashCode();
    }
}
