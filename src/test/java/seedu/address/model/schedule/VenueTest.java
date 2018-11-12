package seedu.address.model.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class VenueTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidVenue_throwsIllegalArgumentException() {
        String invalidVenue = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Venue(invalidVenue));
    }

    @Test
    public void isValidVenue() {
        // null venue
        Assert.assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid venue
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid venue
        assertTrue(Venue.isValidVenue("Level 2"));
        assertTrue(Venue.isValidVenue("-")); // one character
        // long venue
        assertTrue(Venue.isValidVenue("31 Lower Kent Ridge Rd, #01-02 Yusof Ishak House, Singapore 119078"));
    }

    @Test
    public void hashCode_sameObject_equals() {
        Venue venue = new Venue("Office");
        int expectedHash = venue.hashCode();
        assertEquals(venue.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Venue venue = new Venue("Office");
        int expectedHash = venue.hashCode();
        Venue sameVenue = new Venue("Office");
        assertEquals(sameVenue.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Venue venue = new Venue("Office");
        int expectedHash = venue.hashCode();
        Venue differentVenue = new Venue("Counter");
        assertNotEquals(differentVenue.hashCode(), expectedHash);
    }
}
