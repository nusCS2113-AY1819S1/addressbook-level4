package seedu.address.model.person;

import static java.util.Objects.requireNonNull;


/**
 * This holds the patient's medical history
 */

public class MedHistory {

    public final String value;

    public MedHistory(String medhistory) {
        requireNonNull(medhistory);
        value = medhistory;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedHistory // instanceof handles nulls
                && value.equals(((MedHistory) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
