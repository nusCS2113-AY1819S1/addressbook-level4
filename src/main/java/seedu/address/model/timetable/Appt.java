package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Appt {
    public final String value;

    public Appt(String apptInfo) {
        requireNonNull(apptInfo);
        value = apptInfo;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Appt // instanceof handles nulls
            && value.equals(((Appt) other).value)); // state check
    }
}
