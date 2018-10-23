package seedu.address.model.user;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Username in the loginInfo list.
 */
public class UserName {
    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}]*";

    private String userName;

    public UserName(){ }

    public UserName(String userName) {
        requireNonNull(userName);
        this.userName = userName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidUserName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString () {
        return userName;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserName // instanceof handles nulls
                && userName.equals(((UserName) other).userName)); // state check
    }
    public boolean equals(String userName) {
        return this.userName.equals (userName);
    }
}

