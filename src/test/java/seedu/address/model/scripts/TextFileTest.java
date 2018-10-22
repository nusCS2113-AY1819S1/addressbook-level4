package seedu.address.model.scripts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.script.TextFile;
import seedu.address.testutil.Assert;

public class TextFileTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TextFile(null));
    }

    @Test
    public void constructor_invalidCommandType_throwsIllegalArgumentException() {
        String invalidTextFile = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TextFile(invalidTextFile));
    }

    @Test
    public void isValidTextFile() {
        // null command
        Assert.assertThrows(NullPointerException.class, () -> TextFile.isValidTextFile(null));

        // blank command
        assertFalse(TextFile.isValidTextFile("")); // empty string
        assertFalse(TextFile.isValidTextFile(" ")); // spaces only

        // invalid commands
        assertFalse(TextFile.isValidTextFile("abc:"));
        assertFalse(TextFile.isValidTextFile("/efg"));
        assertFalse(TextFile.isValidTextFile("*123"));
        assertFalse(TextFile.isValidTextFile("?123"));
        assertFalse(TextFile.isValidTextFile("<123"));
        assertFalse(TextFile.isValidTextFile(">123"));

        // valid commands
        assertTrue(TextFile.isValidTextFile("studentlist"));
        assertTrue(TextFile.isValidTextFile("mutipleGroup.abc"));
        assertTrue(TextFile.isValidTextFile("studentlist@"));
        assertTrue(TextFile.isValidTextFile("addgroup#"));
        assertTrue(TextFile.isValidTextFile("addgroup$"));
        assertTrue(TextFile.isValidTextFile("addgroup%"));
        assertTrue(TextFile.isValidTextFile("addgroup^"));
        assertTrue(TextFile.isValidTextFile("addgroup&"));
    }
}
