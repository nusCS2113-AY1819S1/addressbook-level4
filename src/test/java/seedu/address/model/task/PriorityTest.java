package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidPriority = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // space only
        assertFalse(Priority.isValidPriority("h")); // not in correct format
        assertFalse(Priority.isValidPriority("HIGH&")); // not in correct format

        // valid priority
        assertTrue(Priority.isValidPriority("HIGH"));
        assertTrue(Priority.isValidPriority("high"));
        assertTrue(Priority.isValidPriority("MED"));
    }
}
