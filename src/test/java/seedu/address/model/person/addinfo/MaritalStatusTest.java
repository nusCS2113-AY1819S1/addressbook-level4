package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MaritalStatusTest {

    @Test
    public void isValidMaritalStatus() {
        assertTrue(MaritalStatus.isValidMaritalStatus("M"));
        assertTrue(MaritalStatus.isValidMaritalStatus("D"));

        assertFalse(MaritalStatus.isValidMaritalStatus("A"));
        assertFalse(MaritalStatus.isValidMaritalStatus("1"));
    }

    @Test
    public void maritalStatusToString() {
        MaritalStatus maritalStatus = new MaritalStatus("M");
        assertEquals(maritalStatus.toString(), "Married");
    }
}
