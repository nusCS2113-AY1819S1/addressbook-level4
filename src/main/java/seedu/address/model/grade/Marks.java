package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
/**
 * Represents a Marks in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMarks(String)}
 */

public class Marks {

    public static final String MESSAGE_MARKS_CONSTRAINTS =
            "marks should be >=0 && <= 100";
    public static final String MARKS_VALIDATION_REGEX = "^([0-9]|[1-9][0-9]|100)$";
    public final String value;

    /**
     * Constructs a {@code marks}.
     *
     * @param mark A valid number.
     */
    public Marks(String mark) {
        requireNonNull(mark);
        checkArgument(isValidMarks(mark), MESSAGE_MARKS_CONSTRAINTS);
        value = mark;
    }

    /**
     * Returns true if a given string is a valid mark number.
     */
    public static boolean isValidMarks(String test) {
        return test.matches(MARKS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Marks // instanceof handles nulls
                && value.equals(((Marks) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
