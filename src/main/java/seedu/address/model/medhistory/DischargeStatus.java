package seedu.address.model.medhistory;

import static java.util.Objects.requireNonNull;

//@@author xShaivan
/**
 * Represents the discharge status of medical history.
 * Guarantees: immutable; is valid as declared in {@link #isValidDischargeStatus(String)}
 */
public class DischargeStatus {

    public static final String MESSAGE_DISCHARGE_STATUS_CONSTRAINTS =
            "Discharge status must not be left blank.";

    public static final String DISCHARGE_STATUS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code DischargeStatus}.
     *
     * @param dischargeStatus A valid discharge status.
     */
    public DischargeStatus(String dischargeStatus) {
        requireNonNull(dischargeStatus);
        value = dischargeStatus;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DischargeStatus // instanceof handles nulls
                && value.equals(((DischargeStatus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
