package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicalreport.Information;
import seedu.address.model.medicalreport.MedicalReport;
import seedu.address.model.medicalreport.ReportDate;
import seedu.address.model.medicalreport.Title;

/**
 * JAXB-friendly version of the Report.
 */
public class XmlAdaptedReport {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Report's %s field is missing!";

    @XmlElement
    private String title;
    @XmlElement
    private String date;
    @XmlElement
    private String information;

    /**
     * Constructs an XmlAdaptedReport.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReport() {}

    /**
     * Constructs a {@code XmlAdaptedReport} with the given report details.
     */
    public XmlAdaptedReport(String title, String date, String information) {
        this.title = title;
        this.date = date;
        this.information = information;
    }

    /**
     * Converts a given Report into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReport
     */
    public XmlAdaptedReport(MedicalReport source) {
        title = source.getTitle().toString();
        date = source.getReportDate().toString();
        information = source.getInformation().toString();
    }

    /**
     * Converts this jaxb-friendly adapted appt object into the model's Report object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted report
     */
    public MedicalReport toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReportDate.class.getSimpleName()));
        }
        final ReportDate modelDate = new ReportDate(date);

        if (information == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Information.class.getSimpleName()));
        }
        final Information modelInformation = new Information(information);

        return new MedicalReport(modelTitle, modelDate, modelInformation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReport)) {
            return false;
        }

        XmlAdaptedReport otherMedicalReport = (XmlAdaptedReport) other;
        return Objects.equals(title, otherMedicalReport.title)
                && Objects.equals(date, otherMedicalReport.date)
                && Objects.equals(information, otherMedicalReport.information);
    }
}

