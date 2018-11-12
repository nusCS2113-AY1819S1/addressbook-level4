package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's checked in time in the address book.
 * Guarantees: immutable; is always valid
 */
public class CheckedInTime {
    public final String value;
    public CheckedInTime(String checkedInTime) {
        requireNonNull(checkedInTime);
        value = checkedInTime;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CheckedInTime // instanceof handles nulls
            && value.equals(((CheckedInTime) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
