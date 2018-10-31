package seedu.address.model.grade;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a grade in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {
    public static final String MESSAGE_GRADE_CONSTRAINTS =
            "Grades only A,B,....D and F";
    public static final String GRADE_VALIDATION_REGEX = ".*";
    public final String value;

    /**
     * Constructs a {@code grade}.
     *
     * @param grade A valid grade number.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_GRADE_CONSTRAINTS);
        value = grade;
    }

    /**
     * Returns true if a given string is a valid grade number.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(GRADE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && value.equals(((Grade) other).value)); // state check
    }


    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
