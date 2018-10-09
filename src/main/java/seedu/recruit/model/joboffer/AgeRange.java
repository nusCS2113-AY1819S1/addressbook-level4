package seedu.recruit.model.joboffer;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents the desired age range of candidate for a job offer
 */

public class AgeRange {

    public static final String MESSAGE_AGE_RANGE_CONSTRAINTS =
            "Age range should only contain numeric characters and only one dash\n"
            + "Example: 18-30";

    public static final String AGE_RANGE_VALIDATION_REGEX = "[\\d]{1,2}[-][\\d]{1,2}";

    public final String value;

    public AgeRange(String ageRangeInput) {
        requireNonNull(ageRangeInput);
        checkArgument(isValidAgeRange(ageRangeInput), MESSAGE_AGE_RANGE_CONSTRAINTS);
        value = ageRangeInput;
    }

    /**
     * Returns true if a given string is a valid ageRange.
     */
    public static boolean isValidAgeRange(String test) {
        return test.matches(AGE_RANGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AgeRange // instanceof handles nulls
                && value.equals(((AgeRange) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
