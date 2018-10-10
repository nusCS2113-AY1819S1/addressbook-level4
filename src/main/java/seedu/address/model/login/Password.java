package seedu.address.model.login;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password only accepts alphanumeric characters, not empty and no spaces";

    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullPassword;

    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        fullPassword = password;
    }

    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX) && !test.equals("");
    }

    @Override
    public String toString() {
        return fullPassword;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Password
                && fullPassword.equals(((Password) other).fullPassword));
    }

    @Override
    public int hashCode() {
        return fullPassword.hashCode();
    }



}
