package seedu.address.model.person;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.medicalreport.MedicalReport;

public class AddMedicalReportTest {
    @Test
    public void equals() {
        MedicalReport report = new MedicalReport("Hello");
        // same object -> returns true
        assertTrue(report.equals(report));
        // same values -> returns true
        MedicalReport medicalReportCopy = new MedicalReport(report.value);
        assertTrue(report.equals(medicalReportCopy));
        // different types -> returns false
        assertFalse(report.equals(1));
        // null -> returns false
        assertFalse(report == null);
        // different report -> returns false
        MedicalReport differentMedicalReport = new MedicalReport("Bye");
        assertFalse(report.equals(differentMedicalReport));
    }
}
