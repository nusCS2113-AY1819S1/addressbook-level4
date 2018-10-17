package seedu.address.model.medicalreport;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person's medical report in the health book.
 */
public class MedicalReport {

    private final Title title;
    private final ReportDate reportDate;
    private final Information information;

    public MedicalReport(Title title, ReportDate reportDate, Information information) {
        requireAllNonNull(title, reportDate, information);
        this.title = title;
        this.reportDate = reportDate;
        this.information = information;
    }

    public Title getTitle() {
        return title;
    }

    public ReportDate getReportDate() {
        return reportDate;
    }

    public Information getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return title.toString() + " " + reportDate.toString() + " " + information.toString() + " ";
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
                && otherMedicalReport.getReportDate().equals(getReportDate())
                && otherMedicalReport.getInformation().equals(getInformation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, reportDate, information);
    }
}
