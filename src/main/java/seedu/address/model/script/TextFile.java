package seedu.address.model.script;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a text file name.
 * Guarantees: immutable; is valid as declared in {@link #isValidTextFile(String)}
 */
public class TextFile {

    public static final String MESSAGE_MESSAGE_CONSTRAINTS = "Text file name contains invalid characters";
    public static final String MESSAGE_VALIDATION_REGEX = "^[^<>?/:*\\s]+$";
    public final String value;

    /**
     * Constructs a {@code TextFile}.
     *
     * @param textFile A command to be executed.
     */
    public TextFile(String textFile) {
        requireNonNull(textFile);
        checkArgument(isValidTextFile(textFile), MESSAGE_MESSAGE_CONSTRAINTS);
        value = textFile;
    }

    /**
     * Returns true if a given string is a valid text file name.
     */
    public static boolean isValidTextFile(String test) {
        return test.matches(MESSAGE_VALIDATION_REGEX);
    }

    public String getTextFile() {
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TextFile // instanceof handles nulls
                && value.equals(((TextFile) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
