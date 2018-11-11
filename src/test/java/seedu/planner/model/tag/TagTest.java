package seedu.planner.model.tag;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.planner.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag format
        assertFalse(Tag.isValidTagName("")); // empty string not allowed
        assertFalse(Tag.isValidTagName("them num")); // spaces are not allowed in tags
        // tag names of more than 20 characters are not allowed
        assertFalse(Tag.isValidTagName("abcdefghijklmnopqrstu"));
        assertFalse(Tag.isValidTagName("something!")); // symbols are not allowed

        // valid tag format
        assertTrue(Tag.isValidTagName("abcdefghijklmnopqrst")); // tag names of 20 characters are allowed
        assertTrue(Tag.isValidTagName("h")); // tag names of a single character are allowed
        assertTrue(Tag.isValidTagName("humming1")); // alphanumeric tag names are allowed
    }

    @Test
    public void equals_tagNameCaseDifferent_same() {
        assertEquals(new Tag("hello"), new Tag("HEllO"));
    }

}
