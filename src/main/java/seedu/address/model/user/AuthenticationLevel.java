package seedu.address.model.user;

import static java.util.Objects.requireNonNull;


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
    public static boolean isValidAuthenticationLevel(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
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