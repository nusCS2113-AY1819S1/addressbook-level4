package seedu.recruit.model.candidate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;



public class EducationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Education(null));
    }

    @Test
    public void constructor_invalidEducation_throwsIllegalArgumentException() {
        String invalidEducation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Education(invalidEducation));
    }

    @Test
    public void isValidEducation() {
        // null education
        Assert.assertThrows(NullPointerException.class, () -> Education.isValidEducation(null));

        // invalid education
        assertFalse(Education.isValidEducation("")); // empty string
        assertFalse(Education.isValidEducation(" ")); // spaces only
        assertFalse(Education.isValidEducation("^")); // non-alphanumeric characters

        // valid education types
        assertTrue(Education.isValidEducation("PRIMARY"));
        assertTrue(Education.isValidEducation("OLEVELS"));
        assertTrue(Education.isValidEducation("NLEVELS"));
        assertTrue(Education.isValidEducation("ALEVELS"));
        assertTrue(Education.isValidEducation("DIPLOMA"));
        assertTrue(Education.isValidEducation("BACHELOR"));
        assertTrue(Education.isValidEducation("MASTER"));
        assertTrue(Education.isValidEducation("PhD"));
        assertTrue(Education.isValidEducation("OTHERS"));
    }
}
