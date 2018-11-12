package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CheckedInTimeTest {

    @Test
    public void equals() {
        CheckedInTime checkedInTime = new CheckedInTime("12:00:00");
        // same object -> returns true
        assertTrue(checkedInTime.equals(checkedInTime));
        // same values -> returns true
        CheckedInTime checkedInTimeCopy = new CheckedInTime(checkedInTime.value);
        assertTrue(checkedInTime.equals(checkedInTimeCopy));
        // different types -> returns false
        assertFalse(checkedInTime.equals(1));
        // null -> returns false
        assertFalse(checkedInTime.equals(null));
        // different checkedInTime -> returns false
        CheckedInTime differentCheckedInTime = new CheckedInTime("10:00:00");
        assertFalse(checkedInTime.equals(differentCheckedInTime));
    }

    @Test
    public void hashCode_sameObject_equals() {
        CheckedInTime checkedInTime = new CheckedInTime("10:00:00");
        int expectedHash = checkedInTime.hashCode();
        assertEquals(checkedInTime.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        CheckedInTime checkedInTime = new CheckedInTime("10:00:00");
        int expectedHash = checkedInTime.hashCode();
        CheckedInTime sameCheckedInTime = new CheckedInTime("10:00:00");
        assertEquals(sameCheckedInTime.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        CheckedInTime checkedInTime = new CheckedInTime("10:00:00");
        int expectedHash = checkedInTime.hashCode();
        CheckedInTime differentCheckedInTime = new CheckedInTime("12:00:00");
        assertNotEquals(differentCheckedInTime.hashCode(), expectedHash);
    }
}
