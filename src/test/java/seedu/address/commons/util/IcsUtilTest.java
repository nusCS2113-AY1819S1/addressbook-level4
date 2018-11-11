package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;

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

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //read
    @Test
    public void readTimeTableFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().readTimeTableFromFile(null, ZONE_ID);
    }

    @Test
    public void readTimeTableFromFile_missingFile_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        IcsUtil.getInstance().readTimeTableFromFile(MISSING_FILE, ZONE_ID);
    }

    @Test
    public void readTimeTableFromFile_emptyFile_successReturnsOptionalIsNotPresent() throws Exception {
        TimeTable dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(EMPTY_FILE, ZONE_ID);
        assert (dataFromFile.isEmpty());
    }

    //tests able to read.
    @Test
    public void readTimeTableFromFile_validFile_successReturnsOptionalIsPresent() throws Exception {
        TimeTable dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(REAL_FILE, ZONE_ID);
        assert (!dataFromFile.isEmpty());
        assertEquals(11, dataFromFile.getTimeSlots().size());
    }

    //save
    @Test
    public void saveTimeTableToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, ZONE_ID, null);
    }

    @Test
    public void saveTimeTableToFile_nullTimeTable_throwsIoException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(null, ZONE_ID, TEMP_FILE);
    }

    @Test
    public void saveTimeTableToFile_validParams_success() throws Exception {
        //no exception expected; successful
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, ZONE_ID, TEMP_FILE);
    }

    //saves the typical timetable, and then reads it again. tests that the timetables are the same.
    @Test
    public void saveThenRead_validParams_success() throws Exception {
        IcsUtil.getInstance().saveTimeTableToFile(TYPICAL_TIMETABLE, ZONE_ID, TEMP_FILE);

        TimeTable expected = IcsUtil.getInstance().readTimeTableFromFile(TEMP_FILE, ZONE_ID);

        assertEquals(expected, TYPICAL_TIMETABLE);
    }
}
