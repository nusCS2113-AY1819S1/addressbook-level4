package seedu.address.model.appt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ApptInfoTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ApptInfo(null));
    }

    @Test
    public void constructor_invalidApptInfo_throwsIllegalArgumentException() {
        String invalidApptInfo = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ApptInfo(invalidApptInfo));
    }

    @Test
    public void isValidApptInfo() {
        // null appt info
        Assert.assertThrows(NullPointerException.class, () -> ApptInfo.isValidApptInfo(null));

        // invalid appt info
        assertFalse(ApptInfo.isValidApptInfo("")); // empty string
        assertFalse(ApptInfo.isValidApptInfo(" ")); // spaces only

        // valid appt info
        assertTrue(ApptInfo.isValidApptInfo("Diabetes Appointment"));
        assertTrue(ApptInfo.isValidApptInfo("-")); // one character
    }
}
