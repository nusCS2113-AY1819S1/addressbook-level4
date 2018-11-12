package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author linnnruoo
public class ContentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        String invalidContent = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Content(invalidContent));
    }

    @Test
    public void isValidContent() {
        // null content
        Assert.assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid content
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only

        // valid content
        assertTrue(Content.isValidContent("Buy tomato at NTUC otw back to Tembusu"));
        assertTrue(Content.isValidContent("-")); // one character
        assertTrue(Content.isValidContent("Buy tomato, cucumber, potato, sweets, chocolate at NTUC.")); // long content
    }
}
