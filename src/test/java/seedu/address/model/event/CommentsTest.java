package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CommentsTest {

    @org.junit.Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Comments(null));
    }

    @org.junit.Test
    public void constructor_invalidContact_throwsIllegalArgumentException() {
        String invalidComment = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Comments(invalidComment));
    }

    @Test
    public void isValidComment() {
        // null contact
        Assert.assertThrows(NullPointerException.class, () -> Comments.isValidComment(null));

        // invalid contact
        assertFalse(Comments.isValidComment("")); // empty string
        assertFalse(Comments.isValidComment(" ")); // spaces only
        assertFalse(Comments.isValidComment("^")); // only non-alphanumeric characters
        assertFalse(Comments.isValidComment("peter*")); // contains non-alphanumeric characters

        // valid contact
        assertTrue(Comments.isValidComment("peter jack")); // alphabets only
        assertTrue(Comments.isValidComment("12345")); // numbers only
        assertTrue(Comments.isValidComment("peter the 2nd")); // alphanumeric characters
        assertTrue(Comments.isValidComment("Capital Tan")); // with capital letters
        assertTrue(Comments.isValidComment("David Roger Jackson Ray Jr 2nd")); // long contact names
    }
}