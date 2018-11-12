package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BodyTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidBody = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Body(invalidBody));
    }

    @Test
    public void isValidBody() {
        // null body
        Assert.assertThrows(NullPointerException.class, () -> Body.isValidBody(null));

        // invalid body
        assertFalse(Body.isValidBody("")); // empty string
        assertFalse(Body.isValidBody(" ")); // spaces only

        // valid body
        assertTrue(Body.isValidBody("CG2027 Assign2"));
        assertTrue(Body.isValidBody("-")); // one character
        assertTrue(Body.isValidBody("CG2027 Asssignment submission by Tue, ivle or hardcopy")); // long body
    }
}
