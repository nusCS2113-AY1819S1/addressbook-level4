package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.ScriptSetupTest.SCRIPTS_LOCATION;
import static seedu.address.storage.ScriptSetupTest.TEST_FILES_LOCATION;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.Assert;

public class FileUtilTest {
    private static final String TestMessage = "HelloWorld\n";
    private static final String helloWorldText = "HelloWorld";
    private static final String ReadOnlyFile = "ReadOnly";
    private static final String TextExtension = ".txt";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void writeToTextFile_success() throws IOException {
        File helloWorldTestFile = new File(FileUtil.getRootLocation()
                + TEST_FILES_LOCATION + helloWorldText + TextExtension);
        File helloWorldFile = new File(FileUtil.getRootLocation()
                + SCRIPTS_LOCATION + helloWorldText + TextExtension);
        FileUtil.writeToTextFile(helloWorldFile, TestMessage);
        boolean isTwoEqual = FileUtils.contentEquals(helloWorldFile, helloWorldTestFile);
        assertEquals(isTwoEqual, true);
    }

    @Test
    public void writeToTextFile_fail_throwException() throws IOException {
        File readOnlyFile = new File(FileUtil.getRootLocation()
                + TEST_FILES_LOCATION + ReadOnlyFile + TextExtension);
        thrown.expect(IOException.class);
        thrown.expectMessage("File '" + readOnlyFile + "' cannot be written to");
        FileUtil.writeToTextFile(readOnlyFile, TestMessage);
    }
}
