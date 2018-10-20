package seedu.address.model.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a email message for EmailUtil.
 * Guarantees: immutable; is valid as declared in {@link #isValidMessage(String)}
 */
public class Message {

    public static final String MESSAGE_MESSAGE_CONSTRAINTS =
            "Message can only be 25MB in size (Gmail).";
    public static final String MESSAGE_VALIDATION_REGEX = "^.{0,25000000}$";
    public final String value;

    /**
     * Constructs a {@code Message}.
     *
     * @param message A valid message body.
     */
    public Message(String message) {
        requireNonNull(message);
        checkArgument(isValidMessage(message), MESSAGE_MESSAGE_CONSTRAINTS);
        value = message;
    }

    /**
     * Returns true if a given string is a valid message.
     */
    public static boolean isValidMessage(String test) {
        return test.matches(MESSAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Message // instanceof handles nulls
                && value.equals(((Message) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
