package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


/**
 * This is a test class to test the validity of course names.
 */
public class FacultyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FacultyName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FacultyName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> FacultyName.isValidFacultyName(null));

        // invalid name
        assertFalse(FacultyName.isValidFacultyName("")); // empty string
        assertFalse(FacultyName.isValidFacultyName("^")); // only non-alphanumeric characters
        assertFalse(FacultyName.isValidFacultyName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(FacultyName.isValidFacultyName("Faculty of Engineering"));
        assertTrue(FacultyName.isValidFacultyName("School of Computing"));
        assertTrue(FacultyName.isValidFacultyName("Faculty of Arts and Social Science"));
    }
}
