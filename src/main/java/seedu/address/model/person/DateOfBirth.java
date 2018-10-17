package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents Date of Birth in Health Book
 */
public class DateOfBirth {
    public final Date dateOfBirth;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Constructs a {@code DateOfBirth}
     * @param dobInput a valid date
     * @throws ParseException if date format is invalid
     */
    public DateOfBirth(String dobInput) throws ParseException {
        requireNonNull(dobInput);
        dateOfBirth = dateFormatter.parse(dobInput);
    }

    @Override
    public String toString() {
        return dateFormatter.format(dateOfBirth);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && dateOfBirth.equals(((DateOfBirth) other).dateOfBirth));
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }
}
