package seedu.address.model.person.addinfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GenderTest {

    @Test
    public void equals() {
        Gender gender = new Gender("F");
        Gender defaultGender = new Gender("");

        assertTrue(gender.equals(gender));

        assertFalse(gender.equals(new Gender("M")));

        assertTrue(defaultGender.equals(new Gender("")));
    }
}
