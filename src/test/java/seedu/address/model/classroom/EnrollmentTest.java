package seedu.address.model.classroom;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EnrollmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Enrollment(null));
    }

    @Test
    public void constructor_invalidCEnrollment_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Enrollment(invalidName));
    }

    @Test
    public void isValidEnrollment(){
        // null maxEnrollment
        Assert.assertThrows(NullPointerException.class, () -> Enrollment.isValidEnrollment(null));

        // invalid maxEnrollment
        assertFalse(Enrollment.isValidEnrollment("")); // empty string
        assertFalse(Enrollment.isValidEnrollment(" ")); // spaces only
        assertFalse(Enrollment.isValidEnrollment("^")); // only non-alphanumeric characters
        assertFalse(Enrollment.isValidEnrollment("22*")); // contains non-alphanumeric characters
        assertFalse(Enrollment.isValidEnrollment("426")); // more than the maximum allowed enrollment size
        assertFalse(Enrollment.isValidEnrollment("0")); // maxEnrollment size cannot be 0
        assertFalse(Enrollment.isValidEnrollment("000")); // enrollment size cannot be 000
        assertFalse(Enrollment.isValidEnrollment("001")); // enrollment size cannot be 001
        assertFalse(Enrollment.isValidEnrollment("1O9")); // contains letter O instead of 0

        // valid maxEnrollment
        assertTrue(Enrollment.isValidEnrollment("1")); // numeric - mnimum enrollment sized allowed (1)
        assertTrue(Enrollment.isValidEnrollment("2")); // another single number
        assertTrue(Enrollment.isValidEnrollment("69")); // double number only
        assertTrue(Enrollment.isValidEnrollment("222")); // triple numbers only
        assertTrue(Enrollment.isValidEnrollment("425")); // maximum - enrollmentsize allowed (425)
    }

    @Test
    public void equals() {
        Enrollment maxEnrollment = new Enrollment("20");
        // same object -> returns true
        assertTrue(maxEnrollment.equals(maxEnrollment));
        // same values -> returns true
        Enrollment maxEnrollmentCopy = new Enrollment(maxEnrollment.getValue());
        assertTrue(maxEnrollment.equals(maxEnrollmentCopy));
        // different types -> returns false
        assertFalse(maxEnrollment.equals(1));
        // null -> returns false
        assertFalse(maxEnrollment.equals(null));
        // different maxEnrollment -> returns false
        Enrollment differentMaxEnrollment = new Enrollment("19");
        assertFalse(maxEnrollment.equals(differentMaxEnrollment));
    }
}
