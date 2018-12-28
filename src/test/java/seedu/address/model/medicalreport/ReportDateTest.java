package seedu.address.model.medicalreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author chewkahmeng
public class ReportDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ReportDate(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ReportDate(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> ReportDate.isValidDate(null));

        // blank date
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // invalid date
        assertFalse(ReportDate.isValidDate("01/01/2018")); // date not in dd-mm-yyyy format
        assertFalse(ReportDate.isValidDate("0a-02-2018")); // alphabets not allowed

        // valid date
        assertTrue(ReportDate.isValidDate("01-01-2018"));
    }
}
