package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@@author tenvinc
/**
 * Represents a month with year information in the financial planner.
 */
public class Month {

    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "Month parameter should be in the format of month-yyyy "
                    + "with month being the standard 3-letter representations of a month and yyyy as a 4 digit number"
                    + "\nPlease take note that inappropriate month will result in errors, for example: sev1en-2018";
    public static final String MESSAGE_MONTH_LOGICAL_CONSTRAINTS =
            "Month should follow the modern calendar.\n"
                    + "Please take note that the month parameter is not case-sensitive but \n"
                    + "it must use the 3-letter representation.\n"
                    + "For eg. feb or FeB or FEB are both accepted but February is not accepted.\n";
    public static final String MONTH_VALIDATION_REGEX = "[a-zA-Z]{3}-\\d{4}";
    public static final Pattern MONTH_VALIDATION_PATTERN = Pattern.compile("(?<month>[a-zA-Z]{3})-(?<year>\\d{4})");

    public static final List<String> STANDARD_MONTHS = Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");
    public static final String STANDARD_MONTH_REP = "%s-%d";

    public final String value;

    private int year;
    private int month;

    /**
     * Constructs a {@code Month}.
     *
     * @param input A valid string.
     */
    public Month(String input) {
        requireNonNull(input);
        checkArgument(isValidMonth(input), MESSAGE_MONTH_CONSTRAINTS);
        initMonth(input);
        value = String.format(STANDARD_MONTH_REP, STANDARD_MONTHS.get(month - 1), year);
    }

    public Month(int month, int year) {
        this.month = month;
        this.year = year;
        checkArgument(month <= STANDARD_MONTHS.size(), MESSAGE_MONTH_CONSTRAINTS);
        value = String.format("%s-%d", STANDARD_MONTHS.get(month - 1), year);
    }

    /**
     * Initiates the values of month and year after parsing the input
     * @param input input to be parsed
     */
    private void initMonth(String input) {
        Matcher matcher = MONTH_VALIDATION_PATTERN.matcher(input);
        if (!matcher.matches()) {
            checkArgument(false, MESSAGE_MONTH_CONSTRAINTS);
        }
        String month = matcher.group("month");
        String year = matcher.group("year");
        checkArgument(isLogicalMonth(month), MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
        this.year = Integer.parseInt(year);
        this.month = STANDARD_MONTHS.indexOf(month.toUpperCase()) + 1;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    /**
     * Returns if a given string is a valid Month.
     * @param test given string
     */
    public static boolean isValidMonth(String test) {
        requireNonNull(test);
        return test.matches(MONTH_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string makes sense as a Month
     */
    public static boolean isLogicalMonth(String test) {
        requireNonNull(test);
        return STANDARD_MONTHS.contains(test.toUpperCase());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Month // instanceof handles nulls
                && month == ((Month) other).month
                && year == ((Month) other).year);
    }
}
