package seedu.address.model.appt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ApptDrNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ApptDrName(null));
    }

    @Test
    public void constructor_invalidDrName_throwsIllegalArgumentException() {
        String invalidDrName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ApptDrName(invalidDrName));
    }

    @Test
    public void isValidApptDrName() {
        // null dr name
        Assert.assertThrows(NullPointerException.class, () -> ApptDrName.isValidDrName(null));

        // invalid dr name
        assertFalse(ApptDrName.isValidDrName("")); // empty string
        assertFalse(ApptDrName.isValidDrName(" ")); // spaces only
        assertFalse(ApptDrName.isValidDrName("Dr Tan!")); // only alpha numeric characters allowed

        // valid dr name
        assertTrue(ApptDrName.isValidDrName("Dr Tan"));
        assertTrue(ApptDrName.isValidDrName("Dr Tan 1"));
    }
}
