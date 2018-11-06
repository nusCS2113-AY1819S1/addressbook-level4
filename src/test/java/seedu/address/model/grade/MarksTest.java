package seedu.address.model.grade;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class MarksTest {
    @org.junit.Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Marks(null));
    }

    @org.junit.Test
    public void constructor_invalidMarks_throwsIllegalArgumentException() {
        String invalidMarks = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Marks(invalidMarks));
    }

    @Test
    public void isValidMarks() {
        // null marks
        Assert.assertThrows(NullPointerException.class, () -> Marks.isValidMarks(null));

        // invalid test marks
        assertFalse(Marks.isValidMarks("")); // empty string
        assertFalse(Marks.isValidMarks(" ")); // spaces only
        assertFalse(Marks.isValidMarks("#%)#&(@$_@@")); // special char
        assertFalse(Marks.isValidMarks("JRJWO")); // alphabet
        assertFalse(Marks.isValidMarks("101")); // over limit
        assertFalse(Marks.isValidMarks("-1")); // over limit
        // valid  test marks
        assertTrue(Marks.isValidMarks("100"));
        assertTrue(Marks.isValidMarks("55.3"));
        assertTrue(Marks.isValidMarks("0.0"));
        assertTrue(Marks.isValidMarks("0"));
    }
}
