package seedu.address.model.budgetelements;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NumberOfEventsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new NumberOfEvents(null));
    }

    @Test
    public void constructor_invalidNumberOfEvents_throwsIllegalArgumentException() {
        String invalidNumberOfEvents = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new NumberOfEvents (invalidNumberOfEvents));
    }

    @Test
    public void isValidNumberOfEvents () {
        // null number of events
        Assert.assertThrows(NullPointerException.class, () -> NumberOfEvents.isValidNumberOfEvents(null));

        // invalid number of events
        assertFalse(NumberOfEvents.isValidNumberOfEvents ("")); // empty string
        assertFalse(NumberOfEvents.isValidNumberOfEvents (" ")); // spaces only
        assertFalse(NumberOfEvents.isValidNumberOfEvents ("numberOfEvents")); // non-numeric
        assertFalse(NumberOfEvents.isValidNumberOfEvents ("90p0")); // alphabets within digits
        assertFalse(NumberOfEvents.isValidNumberOfEvents ("93 15")); // spaces within digits

        // valid number of events
        assertTrue(NumberOfEvents.isValidNumberOfEvents ("90"));
        assertTrue(NumberOfEvents.isValidNumberOfEvents ("93121534"));
        assertTrue(NumberOfEvents.isValidNumberOfEvents ("124293842033123")); // long number of events
    }
}
