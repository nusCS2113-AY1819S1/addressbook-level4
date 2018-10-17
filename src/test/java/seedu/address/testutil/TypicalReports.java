package seedu.address.testutil;
import seedu.address.model.medicalreport.MedicalReport;
/**
 * A utility class containing a list of {@code MedicalReport} objects to be used in tests.
 */
public class TypicalReports {
    public static final MedicalReport REPORT_EXAMPLE1 = new ReportBuilder().withTitle("Asthma")
            .withDate("01/01/2018").withInformation("Prescribed XXX medicine, next appointment on 020218.").build();
    public static final MedicalReport REPORT_EXAMPLE2 = new ReportBuilder().withTitle("Depression")
            .withDate("02/02/2018").withInformation("Prescribed XXX medicine, next appointment on 030318.").build();
    public static final MedicalReport EMPTY_MEDICAL_REPORT = new ReportBuilder().withTitle("").withDate("")
            .withInformation("").build();
}
