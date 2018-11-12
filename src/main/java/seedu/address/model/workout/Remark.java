package seedu.address.model.workout;

import static seedu.address.commons.util.AppUtil.checkArgument;

import com.google.common.base.Strings;


/**
 * Represents a Workout's remark in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {
    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark can be empty or a paragraph.";


    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String REMARK_VALIDATION_REGEX = "[^\\s].*";

    public final String fullRemark;


    /**
     * Constructs a {@code Muscle}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        checkArgument(isValidRemark(remark), MESSAGE_REMARK_CONSTRAINTS);
        fullRemark = remark;
    }


    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {

        return test == null || Strings.isNullOrEmpty(test) || test.matches(REMARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {

        return fullRemark;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && fullRemark.equals(((Remark) other).fullRemark)); // state check
    }

    @Override
    public int hashCode() {
        return fullRemark.hashCode();
    }
}

