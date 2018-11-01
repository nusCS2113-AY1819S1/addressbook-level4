//@@author SHININGGGG
package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expenditure's date in expenditure tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should only be in the format of DD-MM-YYYY, and it should not be blank.\n"
                + "DD must be in the range of 01 to 31, and MM must be in the range of 01 to 12.";
    public static final String DATE_VALIDATION_REGEX = "[\\d]{2}" + "-" + "[\\d]{2}" + "-" + "[\\d]{4}";

    private static final int DAY_MAX_LIMIT = 31;
    private static final int DAY_MIN_LIMIT = 1;
    private static final int MONTH_MAX_LIMIT = 12;
    private static final int MONTH_MIN_LIMIT = 1;

    public final String addingDate;

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        addingDate = date;
    }

    /**
     * Returns if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (test.matches(DATE_VALIDATION_REGEX)) {
            String[] numbers = test.split("-");
            int day = Integer.parseInt(numbers[0]);
            int month = Integer.parseInt(numbers[1]);
            if (day > DAY_MAX_LIMIT || day < DAY_MIN_LIMIT) {
                return false;
            } else if (month > MONTH_MAX_LIMIT || month < MONTH_MIN_LIMIT) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return addingDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && addingDate.equals(((Date) other).addingDate)); // state check
    }

    @Override
    public int hashCode() {
        return addingDate.hashCode();
    }
}
