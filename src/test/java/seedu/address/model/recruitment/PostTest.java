package seedu.address.model.recruitment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PostTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Post(null));
    }

    @Test
    public void constructor_invalidPost_throwsIllegalArgumentException() {
        String post = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Post(post));
    }

    @Test
    public void isValidJobPosition() {
        // null job position
        Assert.assertThrows(NullPointerException.class, () -> Post.isValidPost(null));

        // invalid job position
        assertFalse(Post.isValidPost("")); // empty string
        assertFalse(Post.isValidPost("123456")); // non-character
        assertFalse(Post.isValidPost("IT Manager1")); // alphabets with digits
        assertFalse(Post.isValidPost("Information Technologies Manager")); // string exceeds 20 characters

        // valid job position
        assertTrue(Post.isValidPost("IT Manager")); //character with space
        assertTrue(Post.isValidPost("Finance Accountants")); //string not exceeds 20 characters
    }

}
