package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WorkingRateTest {
    @Test
    public void equals() {
        WorkingRate workingRate = new WorkingRate("7.5");

        // same object -> returns true
        assertTrue(workingRate.equals(workingRate));

        // same values -> returns true
        WorkingRate workingRateCopy = new WorkingRate(workingRate.value);
        assertTrue(workingRate.equals(workingRateCopy));

        // different types -> returns false
        assertFalse(workingRate.equals(1));

        // null -> returns false
        assertFalse(workingRate.equals(null));

        // different working rate -> returns false
        WorkingRate differentWorkingRate = new WorkingRate("10");
        assertFalse(workingRate.equals(differentWorkingRate));
    }

    @Test
    public void hashCode_sameObject_equals() {
        WorkingRate workingRate = new WorkingRate("10");
        int expectedHash = workingRate.hashCode();
        assertEquals(workingRate.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        WorkingRate workingRate = new WorkingRate("10");
        int expectedHash = workingRate.hashCode();
        WorkingRate sameWorkingRate = new WorkingRate("10");
        assertEquals(sameWorkingRate.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        WorkingRate workingRate = new WorkingRate("10");
        int expectedHash = workingRate.hashCode();
        WorkingRate differentWorkingRate = new WorkingRate("20");
        assertNotEquals(differentWorkingRate.hashCode(), expectedHash);
    }
}
