package seedu.recruit.model.joboffer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recruit.testutil.Assert;

public class JobTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Job(null));
    }

    @Test
    public void constructor_invalidJob_throwsIllegalArgumentException() {
        String invalidJob = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Job(invalidJob));
    }

    @Test
    public void isValidJob() {
        // null job
        Assert.assertThrows(NullPointerException.class, () -> Job.isValidJob(null));

        // invalid job
        assertFalse(Job.isValidJob("")); // empty string
        assertFalse(Job.isValidJob("^12")); // non-alphabetical characters
        assertFalse(Job.isValidJob("Chef!")); // contains non-alphabetical characters

        // valid job
        assertTrue(Job.isValidJob("Chef")); // alphabets only
        assertTrue(Job.isValidJob("Head Chef")); // alphabets and spaces

    }
}
