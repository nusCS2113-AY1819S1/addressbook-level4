package seedu.address.model.medicalreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFO2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE2;
import static seedu.address.testutil.TypicalReports.REPORT_EXAMPLE1;
import static seedu.address.testutil.TypicalReports.REPORT_EXAMPLE2;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.ReportBuilder;

//@@author chewkahmeng
public class MedicalReportTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicalReport(null, null, null));
    }

    @Test
    public void equals() {
        MedicalReport reportExample1 = new ReportBuilder(REPORT_EXAMPLE1).build();

        // same object -> returns true
        assertTrue(REPORT_EXAMPLE1.equals(REPORT_EXAMPLE1));

        // same values -> returns true
        assertTrue(REPORT_EXAMPLE1.equals(reportExample1));

        // different types -> returns false
        assertFalse(REPORT_EXAMPLE1.equals(5));

        // null -> returns false
        assertFalse(REPORT_EXAMPLE1 == null);

        // different appt -> returns false
        assertFalse(REPORT_EXAMPLE1.equals(REPORT_EXAMPLE2));

        // different title -> returns false
        MedicalReport editedReportExample1 = new ReportBuilder(REPORT_EXAMPLE1).withTitle(VALID_TITLE2).build();
        assertFalse(REPORT_EXAMPLE1.equals(editedReportExample1));

        // different date -> returns false
        editedReportExample1 = new ReportBuilder(REPORT_EXAMPLE1).withDate(VALID_DATE2).build();
        assertFalse(REPORT_EXAMPLE1.equals(editedReportExample1));

        // different information -> returns false
        editedReportExample1 = new ReportBuilder(REPORT_EXAMPLE1).withInformation(VALID_INFO2).build();
        assertFalse(REPORT_EXAMPLE1.equals(editedReportExample1));
    }
}
