package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

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

    @Test
    public void isValidXmlFilename() {
        // valid xml filename
        assertTrue(FileUtil.isValidXmlFilename("someXmlFile.xml"));
        assertTrue(FileUtil.isValidXmlFilename("anotherXmlFile.xml"));
        assertTrue(FileUtil.isValidXmlFilename("someXmlFile.XML"));

        // invalid filename, not .xml
        assertFalse(FileUtil.isValidXmlFilename("someXmlFile.pdf"));

        // invalid filename, empty string
        assertFalse(FileUtil.isValidXmlFilename(""));

        // invalid filename, no extension
        assertFalse(FileUtil.isValidXmlFilename("someXmlFile"));

        // null filename -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidXmlFilename(null));

    }

}
