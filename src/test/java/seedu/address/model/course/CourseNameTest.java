package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;


/**
 * This is a test class to test the validity of course names.
 */
public class CourseNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> CourseName.isValidCourseName(null));

        // invalid name
        assertFalse(CourseName.isValidCourseName("")); // empty string
        assertFalse(CourseName.isValidCourseName("^")); // only non-alphanumeric characters
        assertFalse(CourseName.isValidCourseName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CourseName.isValidCourseName("Computer Engineering"));
        assertTrue(CourseName.isValidCourseName("Computer Science"));
        assertTrue(CourseName.isValidCourseName("Political Science"));
        assertTrue(CourseName.isValidCourseName("Economics"));
        assertTrue(CourseName.isValidCourseName("Medicine"));
    }
}
