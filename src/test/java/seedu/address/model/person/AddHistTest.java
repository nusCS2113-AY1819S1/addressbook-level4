package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddHistTest {

    @Test
    public void equals() {
        MedHistory medhistory = new MedHistory("Hello");

        // same object -> returns true
        assertTrue(medhistory.equals(medhistory));

        // same values -> returns true
        MedHistory medhistoryCopy = new MedHistory(medhistory.value);
        assertTrue(medhistory.equals(medhistoryCopy));

        // different types -> returns false
        assertFalse(medhistory.equals(1));

        // null -> returns false
        assertFalse(medhistory == null);

        // different medhistory -> returns false
        MedHistory differentmedhistory = new MedHistory("Bye");
        assertFalse(medhistory.equals(differentmedhistory));
    }
}
