package seedu.planner.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.planner.testutil.Assert;

public class DirectoryPathTest {

    @Test
    public void constructor_invalidDirectoryPath_throwsIllegalArgumentException() {
        String invalidDirectory = "Hello";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DirectoryPath(invalidDirectory));
    }

    @Test
    public void isValidDirectory() {
        Assert.assertThrows(NullPointerException.class, () -> DirectoryPath.isValidDirectory(null));
        Assert.assertThrows(NullPointerException.class, () -> DirectoryPath.isValidFilePath(null));

        assertFalse(DirectoryPath.isValidDirectory("Hello"));
        assertFalse(DirectoryPath.isValidFilePath("Hello"));
        assertTrue(DirectoryPath.isValidDirectory("C:\\\\"));
        assertFalse(DirectoryPath.isValidFilePath("C:\\\\"));
        assertFalse(DirectoryPath.isValidDirectory("C:\\\\Hello\\Kitty"));
        assertFalse(DirectoryPath.isValidFilePath("C:\\\\Hello\\Kitty"));
        String directoryPathHome = DirectoryPath.getDefaultHomeDirectoryValue();
        String directoryPathWorking = DirectoryPath.getDefaultWorkingDirectoryValue();
        assertTrue(DirectoryPath.isValidDirectory(directoryPathHome));
        assertTrue(DirectoryPath.isValidDirectory(directoryPathWorking));
        assertFalse(DirectoryPath.isValidFilePath(directoryPathHome));
        assertFalse(DirectoryPath.isValidFilePath(directoryPathWorking));
    }
}
