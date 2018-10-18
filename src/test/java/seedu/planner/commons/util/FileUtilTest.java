package seedu.planner.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.planner.testutil.Assert;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}
