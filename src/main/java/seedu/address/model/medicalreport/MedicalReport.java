package seedu.address.model.medicalreport;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's medical report in the health book.
 */
public class MedicalReport {

    public final String value;

    public MedicalReport(String report) {
        requireNonNull(report);
        value = report;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicalReport // instanceof handles nulls
                && value.equals(((MedicalReport) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
