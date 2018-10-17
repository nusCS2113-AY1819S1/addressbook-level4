package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NricTest {

    @Test
    public void equals() {
        Nric nric = new Nric("S9118766C");

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // same value -> returns true
        Nric nricCopy = new Nric(nric.value);
        assertTrue(nric.equals(nricCopy));

        // different types -> returns false
        assertFalse(nric.equals(1));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different nric -> returns false
        Nric differentNric = new Nric("T0649716G");
        assertFalse(nric.equals(differentNric));
    }
}
