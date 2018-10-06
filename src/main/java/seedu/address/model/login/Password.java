package seedu.address.model.login;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password only accepts alphanumeric characters, not empty and no spaces";

    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String password;

    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.password = password;
    }

    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX) && !test.equals("");
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Password
                && this.password.equals(((Password) other).password));
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }



}
