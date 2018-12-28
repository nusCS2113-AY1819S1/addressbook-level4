package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NricTest {
    public static final String VALID_NRIC = "S5949460J";
    public static final String INVALID_NRIC = "A0136825E";

    @Test
    public void isValidNric() {
        assertFalse(Nric.isValidNric(INVALID_NRIC));
        assertTrue(Nric.isValidNric(VALID_NRIC));
    }

    @Test
    public void isCorrectNric() {
        // Correct NRIC based on checksum
        assertTrue(Nric.isCorrectNric("T0766121A"));
        assertTrue(Nric.isCorrectNric("S0680317B"));
        assertTrue(Nric.isCorrectNric("S1731028C"));
        assertTrue(Nric.isCorrectNric("S7332814D"));
        assertTrue(Nric.isCorrectNric("S4776352E"));
        assertTrue(Nric.isCorrectNric("S3812806Z"));

        //Incorrect NRIC based on checksum
        assertFalse(Nric.isCorrectNric("S1723758A"));
        assertFalse(Nric.isCorrectNric("S8160045B"));
        assertFalse(Nric.isCorrectNric("S8337569C"));
        assertFalse(Nric.isCorrectNric("S8191004D"));
        assertFalse(Nric.isCorrectNric("S5949460E"));

    }

    @Test
    public void nricToString() {
        Nric nric = new Nric(VALID_NRIC);

        assertEquals(nric.toString(), VALID_NRIC);
    }

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
