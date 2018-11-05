package seedu.address.model.grade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TestNameTest {
    @org.junit.Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TestName(null));
    }

    @org.junit.Test
    public void constructor_invalidTestName_throwsIllegalArgumentException() {
        String invalidTestName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TestName(invalidTestName));
    }

    @Test
    public void isValidTestName() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> TestName.isValidTestName(null));

        // invalid testname
        assertFalse(TestName.isValidTestName("")); // empty string
        assertFalse(TestName.isValidTestName(" ")); // spaces only
        assertFalse(TestName.isValidTestName("#%)#&(@$_@@")); // special char

        // valid  testname
        assertTrue(TestName.isValidTestName("MA15097"));
        assertTrue(TestName.isValidTestName("MATH"));
        assertTrue(TestName.isValidTestName("M4020222222"));
    }
}
