
package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Password in the loginInfo list.
 */
public class Password {
    /*
     * The password must not contain a whitespace which include " "
     */

    public static final int MAX_LENGTH_FOR_PASSWORD = 50;
    public static final String PASSWORD_VALIDATION_REGEX = "[\\S]*";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumeric characters and no space, and it should not be blank";
    public static final String MESSAGE_PASSWORD_LENGTH_CONSTRAINTS =
            "Password should only be less than 50 words";

    private String password;

    public Password(){}
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword (password), MESSAGE_PASSWORD_CONSTRAINTS);
        checkArgument (!isPasswordTooLong (password), MESSAGE_PASSWORD_LENGTH_CONSTRAINTS);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX) && !test.isEmpty ();


    }
    /**
     *Return true if userName is longer than {@code MAX_LENGTH_FOR_USERNAME}
     */
    public static boolean isPasswordTooLong(String test) {
        if (test.length () > MAX_LENGTH_FOR_PASSWORD) {
            return true;
        }
        return false;
    }


    @Override
    public String toString () {
        return password;
    }

    public boolean equals(String password) {
        return this.password.equals (password);
    }

}
