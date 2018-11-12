package seedu.address.model.recruitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class WorkExpTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WorkExp(null));
    }

    @Test
    public void constructor_invalidWorkExp_throwsIllegalArgumentException() {
        String workExp = "";
        Assert.assertThrows(NumberFormatException.class, () -> new WorkExp(workExp));
    }

    @Test
    public void isValidWorkExp() {
        // null working experience
        Assert.assertThrows(NullPointerException.class, () -> WorkExp.isValidWorkExp(null));

        // invalid working experience
        assertFalse(WorkExp.isValidWorkExp("")); // empty string
        assertFalse(WorkExp.isValidWorkExp("31")); // digits exceed 30
        assertFalse(WorkExp.isValidWorkExp("IT Manager1")); // alphabets with digits
        assertFalse(WorkExp.isValidWorkExp("-1")); // digits not within 0 to 30

        // valid working experience
        assertTrue(WorkExp.isValidWorkExp("0")); //digits from 0 to 30
        assertTrue(WorkExp.isValidWorkExp("30")); //digits from 0 to 30
        assertTrue(WorkExp.isValidWorkExp("1")); //digits from 0 to 30
    }

}
