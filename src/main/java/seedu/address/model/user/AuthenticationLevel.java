package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.authentication.AuthenticalLevel.AUTH_ACCOUNTANT;
import static seedu.address.authentication.AuthenticalLevel.AUTH_ADMIN;
import static seedu.address.authentication.AuthenticalLevel.AUTH_MANAGER;
import static seedu.address.authentication.AuthenticalLevel.AUTH_STOCK_TAKER;



/**
 * Represents a Username in the loginInfo list.
 */
public class AuthenticationLevel {
    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}]*";

    private String authenticationLevel;

    public AuthenticationLevel(){ }

    public AuthenticationLevel(String authenticationLevel) {
        requireNonNull(authenticationLevel);
        this.authenticationLevel = authenticationLevel;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    /**
     * Returns true if authentication level is enum.
     *
     * @param authenticationLevel User input when create account
     * @return
     */
    private static boolean isAuthenticationLevelValid(String authenticationLevel) {
        if (authenticationLevel.equals (AUTH_ADMIN)
                || authenticationLevel.equals (AUTH_MANAGER)
                || authenticationLevel.equals (AUTH_STOCK_TAKER)
                || authenticationLevel.equals (AUTH_ACCOUNTANT)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString () {
        return authenticationLevel;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AuthenticationLevel // instanceof handles nulls
                && authenticationLevel.equals(((AuthenticationLevel) other).authenticationLevel)); // state check
    }
    public boolean equals(String userName) {
        return this.authenticationLevel.equals (authenticationLevel);
    }
}
