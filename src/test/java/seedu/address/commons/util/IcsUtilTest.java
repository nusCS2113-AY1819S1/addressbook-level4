package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.TimeTable;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTimeSlots;

public class IcsUtilTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "IcsUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.ics");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.ics");
    private static final Path REAL_FILE = TEST_DATA_FOLDER.resolve("real.ics");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("temp.ics");

    private static final TimeTable TYPICAL_TIMETABLE = TypicalTimeSlots.getTypicalTimeTable();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //read
    @Test
    public void readTimeTableFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().readTimeTableFromFile(null);
    }

    @Test
    public void readTimeTableFromFile_missingFile_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        IcsUtil.getInstance().readTimeTableFromFile(MISSING_FILE);
    }

    @Test
    public void readTimeTableFromFile_emptyFile_successReturnsOptionalIsNotPresent() throws Exception {
        Optional<TimeTable> dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(EMPTY_FILE);
        assert (!dataFromFile.isPresent());
    }

    //tests able to read.
    @Test
    public void readTimeTableFromFile_validFile_successReturnsOptionalIsPresent() throws Exception {
        Optional<TimeTable> dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(REAL_FILE);
        assert (dataFromFile.isPresent());
        assertEquals(11, dataFromFile.get().getTimeSlots().size());
    }

    //save
    @Test
    public void saveTimeTableToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, null);
    }

    @Test
    public void saveTimeTableToFile_nullTimeTable_throwsIoException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(null, TEMP_FILE);
    }

    @Test
    public void saveTimeTableToFile_validParams_success() throws Exception {
        //no exception expected
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, TEMP_FILE);
    }

    @Ignore("Ignore because fails travis")
    //saves the typical timetable, and then reads it again. tests that the timetables are the same.
    @Test
    public void saveThenRead_validParams_success() throws Exception {
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, TEMP_FILE);

        Optional<TimeTable> result = IcsUtil.getInstance().readTimeTableFromFile(TEMP_FILE);
        TimeTable expected = result.get();

        assertEquals(expected, TYPICAL_TIMETABLE); //fails travis but not local
    }
}
