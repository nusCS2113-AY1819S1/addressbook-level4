package seedu.address.model.course;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;



/**
 * This is a test class to test the validity of course codes.
 */
public class CourseCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CourseCode(null));
    }

    @Test
    public void constructor_invalidCode_throwsIllegalArgumentException() {
        String invalidCode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CourseCode(invalidCode));
    }

    @Test
    public void isValidCode() {
        // null code
        Assert.assertThrows(NullPointerException.class, () -> CourseCode.isValidCourseCode(null));

        // invalid code
        assertFalse(CourseCode.isValidCourseCode("")); // empty string
        assertFalse(CourseCode.isValidCourseCode("^")); // only non-alphanumeric characters
        assertFalse(CourseCode.isValidCourseCode("peter*")); // contains non-alphanumeric characters
        assertFalse(CourseCode.isValidCourseCode("CEG1")); // course code has numbers
        assertFalse(CourseCode.isValidCourseCode("1234")); // numbers only
        assertFalse(CourseCode.isValidCourseCode("COMPUTERSCIENCE")); // too-long of a course code

        // valid code
        assertTrue(CourseCode.isValidCourseCode("CEG"));
        assertTrue(CourseCode.isValidCourseCode("CS"));
        assertTrue(CourseCode.isValidCourseCode("POLSCI"));
        assertTrue(CourseCode.isValidCourseCode("ECONS"));
        assertTrue(CourseCode.isValidCourseCode("MED"));
    }
}
