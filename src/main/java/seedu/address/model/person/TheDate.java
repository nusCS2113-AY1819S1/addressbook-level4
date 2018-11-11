package seedu.address.model.person;

//import java.text.SimpleDateFormat;
//import java.util.Date;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date of the event in JitHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TheDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should only contain numbers in DDMMYYYY format, and it should not be blank";

    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "(0[1-9]|[1-2][0-9]|3[0-1])(0[1-9]|1[0-2])(\\d{4})";
    //date regex here allows for 31 days every month of a 4 digit year.

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param theDate A valid date.
     */
    public TheDate(String theDate) {
        requireNonNull(theDate);
        checkArgument(isValidDate(theDate), MESSAGE_DATE_CONSTRAINTS);
        value = theDate;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        //        SimpleDateFormat jitHubDateFormat = new SimpleDateFormat("ddMMyyyy");
        //        Date validator = jitHubDateFormat.parse(test);

        return test.matches(DATE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TheDate // instanceof handles nulls
                && value.equals(((TheDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
