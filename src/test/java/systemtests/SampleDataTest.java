package systemtests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.Test;

import seedu.divelog.model.DiveLog;
import seedu.divelog.model.dive.DiveSession;
import seedu.divelog.model.util.SampleDataUtil;
import seedu.divelog.testutil.TestUtil;

public class SampleDataTest extends DiveLogSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected DiveLog getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void diveLog_dataFileDoesNotExist_loadSampleData() {
        DiveSession[] expectedList = SampleDataUtil.getSampleDives();

        Arrays.sort(expectedList);

        for (int i = 0; i < expectedList.length / 2; i++) {
            DiveSession tmp = expectedList[i];
            expectedList[i] = expectedList[expectedList.length - 1 - i];
            expectedList[expectedList.length - 1 - i] = tmp;
        }

        assertListMatching(getDiveListPanel(), expectedList);
    }
}
