package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OccupationTest {

    @Test
    public void isValidOccupation() {
        assertTrue(Occupation.isValidOccupation("Nurse"));
        assertTrue(Occupation.isValidOccupation("nurse"));

        assertFalse(Occupation.isValidOccupation("Dr."));
        assertFalse(Occupation.isValidOccupation("Wood Cutter"));
    }

    @Test
    public void occupationToString() {
        Occupation occupation = new Occupation("Nurse");
        assertEquals(occupation.toString(), "Nurse");
    }
}
