package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EnrollmentTest {
    @Test
    public void equals() {
        Enrollment maxEnrollment = new Enrollment("20");
        // same object -> returns true
        assertTrue(maxEnrollment.equals(maxEnrollment));
        // same values -> returns true
        Enrollment maxEnrollmentCopy = new Enrollment(maxEnrollment.value);
        assertTrue(maxEnrollment.equals(maxEnrollmentCopy));
        // different types -> returns false
        assertFalse(maxEnrollment.equals(1));
        // null -> returns false
        assertFalse(maxEnrollment.equals(null));
        // different maxEnrollment -> returns false
        Enrollment differentMaxEnrollment = new Enrollment("wrongsize");
        assertFalse(maxEnrollment.equals(differentMaxEnrollment));
    }
}
