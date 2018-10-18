package seedu.address.model.medicalreport;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person's medical report in the health book.
 */
public class MedicalReport {

    private final Title title;
    private final ReportDate date;
    private final Information information;

    public MedicalReport(Title title, ReportDate date, Information information) {
        requireAllNonNull(title, date, information);
        this.title = title;
        this.date = date;
        this.information = information;
    }

    public Title getTitle() {
        return title;
    }

    public ReportDate getDate() {
        return date;
    }

    public Information getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return title.toString() + " " + date.toString() + " " + information.toString() + " ";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MedicalReport)) {
            return false;
        }
        MedicalReport otherMedicalReport = (MedicalReport) other;
        return otherMedicalReport.getTitle().equals(getTitle())
                && otherMedicalReport.getDate().equals(getDate())
                && otherMedicalReport.getInformation().equals(getInformation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, date, information);
    }
}
