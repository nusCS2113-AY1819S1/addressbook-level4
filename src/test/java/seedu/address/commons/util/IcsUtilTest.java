package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.TimeTable;
import seedu.address.testutil.TestUtil;

public class IcsUtilTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "IcsUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.ics");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.ics");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("valid.ics");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("temp.ics");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readTimeTableFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().readTimeTableFromFile(null);
    }

    @Test
    public void readTimeTableFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(IOException.class);
        IcsUtil.getInstance().readTimeTableFromFile(MISSING_FILE);
    }

    @Test
    public void readTimeTableFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(IOException.class);
        IcsUtil.getInstance().readTimeTableFromFile(EMPTY_FILE);
    }

    @Test
    public void readTimeTableFromFile_validFile_validResult() throws Exception {
        Optional<TimeTable> dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(VALID_FILE);
        assert (dataFromFile.isPresent());
        assertEquals(11, dataFromFile.get().getTimeSlots().size());
    }

}
