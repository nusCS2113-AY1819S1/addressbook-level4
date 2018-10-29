package seedu.address.model.attendee;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AttendeeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendee(null));
    }

    @Test
    public void constructor_invalidAttendeeName_throwsIllegalArgumentException() {
        String invalidAttendeeName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Attendee(invalidAttendeeName));
    }

    @Test
    public void isValidAttendeeName() {
        // null attendee name
        Assert.assertThrows(NullPointerException.class, () -> Attendee.isValidAttendeeName(null));
    }
}
