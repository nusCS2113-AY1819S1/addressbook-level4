package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents the date of the event in JitHub.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class TheDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should only contain numbers in DDMMYYYY format, and it should not be blank\n"
                    + "Only calendar dates are accepted.";

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
        SimpleDateFormat jitHubDateFormat = new SimpleDateFormat("ddMMyyyy");
        jitHubDateFormat.setLenient(false);
        try {
            jitHubDateFormat.parse(test);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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
