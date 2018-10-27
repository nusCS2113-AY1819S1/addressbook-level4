package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Username in the loginInfo list.
 */
public class UserName {
    /*
     * The  username must not have a whitespace include "";
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}]*";
    public static final String MESSAGE_USER_NAME_CONSTRAINTS =
            "UserName should only contain alphanumeric characters and no space,\nand should not be blank";
    public static final int MAX_LENGTH_FOR_USERNAME = 30;
    public static final String MESSAGE_USER_NAME_LENGTH_CONSTRAINTS =
            "UserName should only be less than 30 words";
    private String userName;

    public UserName(){}

    public UserName(String userName) {
        requireNonNull(userName);
        checkArgument(isValidUserName (userName), MESSAGE_USER_NAME_CONSTRAINTS);
        checkArgument(!isUserNameTooLong (userName), MESSAGE_USER_NAME_LENGTH_CONSTRAINTS);
        this.userName = userName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidUserName(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX) && !test.isEmpty ();
    }

    /**
     *Return true if userName is longer than {@code MAX_LENGTH_FOR_USERNAME}
     */
    public static boolean isUserNameTooLong(String test){
        if (test.length () > MAX_LENGTH_FOR_USERNAME){
            return true;
        }
        return false;
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

