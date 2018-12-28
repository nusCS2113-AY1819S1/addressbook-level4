package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.DateTimeUtil.DATE_VALIDATION_REGEX;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.OptionalInt;

import seedu.address.model.util.DateTimeUtil;

/**
 * Represents ReportDate of Birth in Health Book
 */
public class DateOfBirth {
    public final Optional<LocalDate> value;
    public final OptionalInt age;

    private final String emptyString = "";

    /**
     * Constructs a {@code DateOfBirth}
     * @param value a valid date
     * @throws ParseException if date format is invalid
     */

    public DateOfBirth(String value) {
        requireNonNull(value);
        this.value = (value.isEmpty()) ? Optional.empty() : Optional.of(DateTimeUtil.parseDate(value));
        this.age = calculateAge(this.value);
    }

    /**
     * Calculates the {@code age} based on {@code dateOfBirth}
     */
    private OptionalInt calculateAge(Optional<LocalDate> dateOfBirth) {
        if (dateOfBirth.isPresent()) {
            return OptionalInt.of(Period.between(dateOfBirth.get(), LocalDate.now()).getYears());
        } else {
            return OptionalInt.empty();
        }
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public String ageToString() {
        return age.isPresent() ? "(" + Integer.toString(age.getAsInt()) + ")" : emptyString;
    }

    @Override
    public String toString() {
        return value.isPresent() ? DateTimeUtil.format(value.get()) : emptyString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && value.equals(((DateOfBirth) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
