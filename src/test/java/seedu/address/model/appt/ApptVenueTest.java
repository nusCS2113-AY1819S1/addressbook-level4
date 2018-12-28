package seedu.address.model.appt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ApptVenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ApptVenue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ApptVenue(invalidVenue));
    }

    @Test
    public void isValidApptVenue() {
        // null venues
        Assert.assertThrows(NullPointerException.class, () -> ApptVenue.isValidVenue(null));

        // invalid venues
        assertFalse(ApptVenue.isValidVenue("")); // empty string
        assertFalse(ApptVenue.isValidVenue(" ")); // spaces only

        // valid venues
        assertTrue(ApptVenue.isValidVenue("Consultation Room 12"));
        assertTrue(ApptVenue.isValidVenue("-")); // one character
    }
}
