package seedu.recruit.model.joboffer;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents the desired age range of candidate for a job offer
 */

public class AgeRange {

    public static final String MESSAGE_AGE_RANGE_CONSTRAINTS =
            "Age range should only contain numbers within the range of 16 to 60 (inclusive) and only one dash\n"
            + "Example: 18-30";

    public static final String AGE_RANGE_VALIDATION_REGEX = "[\\d]{1,2}[-][\\d]{1,2}";

    public final String value;

    private int minAge;
    private int maxAge;

    public AgeRange(String ageRangeInput) {
        requireNonNull(ageRangeInput);
        checkArgument(isValidAgeRange(ageRangeInput), MESSAGE_AGE_RANGE_CONSTRAINTS);
        int [] minAndMaxAge = getMinAndMaxAgeFromAgeRange(ageRangeInput);
        minAge = minAndMaxAge[0];
        maxAge = minAndMaxAge[1];
        value = ageRangeInput;
    }

    /**
     * Returns true if a given string is a valid ageRange.
     */
    public static boolean isValidAgeRange(String test) {
        //Checks whether input matches the regex to prevent adverse input 19-
        if (test.matches(AGE_RANGE_VALIDATION_REGEX)) {
            int[] minAndMaxAge = getMinAndMaxAgeFromAgeRange(test);
            int minEnteredAge = minAndMaxAge[0];
            int maxEnteredAge = minAndMaxAge[1];

            return minEnteredAge >= 16 && maxEnteredAge <= 60;
        }
        return false;
    }

    public static int[] getMinAndMaxAgeFromAgeRange (String test) {
        String[] minAndMaxAgeString = test.split("-");
        int[] minAndMaxAgeInt = new int[2];
        if (Integer.parseInt(minAndMaxAgeString[0]) > Integer.parseInt(minAndMaxAgeString[1])) {
            minAndMaxAgeInt[0] = Integer.parseInt(minAndMaxAgeString[1]);
            minAndMaxAgeInt[1] = Integer.parseInt(minAndMaxAgeString[0]);
        }
        else {
            minAndMaxAgeInt[0] = Integer.parseInt(minAndMaxAgeString[0]);
            minAndMaxAgeInt[1] = Integer.parseInt(minAndMaxAgeString[1]);
        }

        return minAndMaxAgeInt;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
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
