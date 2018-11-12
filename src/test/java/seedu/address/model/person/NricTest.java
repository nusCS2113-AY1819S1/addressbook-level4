package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jylee-git
public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "WAPIANG8E";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        //null NRIC param
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        //invalid NRIC param
        assertFalse(Nric.isValidNric("")); //Empty string
        assertFalse(Nric.isValidNric(" ")); // Space only
        assertFalse(Nric.isValidNric("Q1234567E")); //Invalid prefix
        assertFalse(Nric.isValidNric("S1234567E9")); //Invalid length
        assertFalse(Nric.isValidNric("T123R567E")); //Not a 7-digit number

        //Valid NRIC param
        assertTrue(Nric.isValidNric("S1234567E"));
        assertTrue(Nric.isValidNric("F1234567I"));
        assertTrue(Nric.isValidNric("G1234567E"));
        assertTrue(Nric.isValidNric("T1234000E"));
    }

    @Test
    public void hashCode_sameObject_equals() {
        Nric nric = new Nric("S1234567E");
        int expectedHash = nric.hashCode();
        assertEquals(nric.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentObject_equals() {
        Nric nric = new Nric("S1234567E");
        int expectedHash = nric.hashCode();
        Nric sameNric = new Nric("S1234567E");
        assertEquals(sameNric.hashCode(), expectedHash);
    }

    @Test
    public void hashCode_differentValues_notEquals() {
        Nric nric = new Nric("S1234567E");
        int expectedHash = nric.hashCode();
        Nric differentNric = new Nric("F1234567I");
        assertNotEquals(differentNric.hashCode(), expectedHash);
    }
}
