package seedu.planner.model;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.parser.exceptions.ParseException;

/**
 * Represents a month of {@code MonthSummary} in the financial planner.
 */
public class Month {

    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "Month parameter should be in the format of month-yyyy "
                    + "with month being the standard 3-letter representations of a month and yyyy as a 4 digit number"
                    + " Please take note that inappropriate month will result in errors, for example: sev1en-2018";
    public static final String MESSAGE_MONTH_LOGICAL_CONSTRAINTS =
            "Month should follow the modern calendar. "
                    + "month parameter must use the 3-letter representation and is non case-sensitive"
                    + ", for eg. feb or FeB or FEB are both accepted\n";
    public static final String MONTH_VALIDATION_REGEX = "(?<month><[a-zA-Z]{3})-(?<year>\\d{4})";
    public static final Pattern MONTH_VALIDATION_PATTERN = Pattern.compile(MONTH_VALIDATION_REGEX);

    private static final List<String> standardMonths = Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC");

    public final String value = null;

    private Logger logger = LogsCenter.getLogger(Month.class);
    private Matcher matcher;

    private int year;
    private int month;

    /**
     * Constructs a {@code Month}.
     *
     * @param input A valid string.
     */
    public Month(String input) throws ParseException {
        checkArgument(isValidMonth(input), MESSAGE_MONTH_CONSTRAINTS);
        matcher = MONTH_VALIDATION_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_MONTH_CONSTRAINTS);
        }
        checkArgument(isLogicalMonth(matcher.group("month")), MESSAGE_MONTH_LOGICAL_CONSTRAINTS);
        initMonth(matcher.group("month"), matcher.group("year"));
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
        test = test.toUpperCase();
        return standardMonths.contains(test);
    }

    private void initMonth(String month, String year) {
        requireAllNonNull(month, year);
        this.year = Integer.parseInt(year);
        this.month = standardMonths.indexOf(month) + 1;
    }
}
