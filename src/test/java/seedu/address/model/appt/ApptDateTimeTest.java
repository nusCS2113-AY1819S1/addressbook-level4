package seedu.address.model.appt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author brandonccm1996
public class ApptDateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ApptDateTime(null));
    }

    @Test
    public void constructor_invalidApptDateTime_throwsIllegalArgumentException() {
        String invalidApptDateTime = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ApptDateTime(invalidApptDateTime));
    }

    @Test
    public void isValidApptDateTime() {
        // null apptdatetime
        Assert.assertThrows(NullPointerException.class, () -> ApptDateTime.isValidDateTime(null));

        // blank apptdatetime
        assertFalse(ApptDateTime.isValidDateTime("")); // empty string
        assertFalse(ApptDateTime.isValidDateTime(" ")); // spaces only

        // invalid apptdatetime
        assertFalse(ApptDateTime.isValidDateTime("01/01/2018 14:00")); // date not in dd-mm-yyyy format
        assertFalse(ApptDateTime.isValidDateTime("01-01-2018 1400")); // time not in hh:mm format
        assertFalse(ApptDateTime.isValidDateTime("02-02-2018")); // no time given
        assertFalse(ApptDateTime.isValidDateTime("15:00")); // no date given
        assertFalse(ApptDateTime.isValidDateTime("0a-02-2018 15:00")); // alphabets not allowed

        // valid apptdatetime
        assertTrue(ApptDateTime.isValidDateTime("02-02-2018 15:00"));
    }
}
