package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.TimeTable;
import seedu.address.testutil.TestUtil;

public class IcsUtilTest {
    private static final String LABEL = "InsertFunnyLabelName";

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "IcsUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.ics");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.ics");
    private static final Path VALID_FILE_11_TIMESLOTS = TEST_DATA_FOLDER.resolve("valid.ics");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("temp.ics");

    private static final TimeSlot VALID_TIMESLOT = new TimeSlot(DayOfWeek.MONDAY, LocalTime.parse("10:00:00"), LocalTime.parse("12:00:00"), LABEL);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //Reading tests
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
        Optional<TimeTable> dataFromFile = IcsUtil.getInstance().readTimeTableFromFile(VALID_FILE_11_TIMESLOTS);
        assert (dataFromFile.isPresent());
        assertEquals(11, dataFromFile.get().getTimeSlots().size());
    }



    //saving tests
    @Test
    public void saveTimeTableToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(getValidTimeTable(), null);
    }

    @Test
    public void saveTimeTableToFile_nullTimeTable_throwsNullPointerException() throws Exception {
        writeBlankTempFile();
        thrown.expect(NullPointerException.class);
        IcsUtil.getInstance().saveTimeTableToFile(null, TEMP_FILE);
        writeBlankTempFile();
    }


    @Test
    public void saveTimeTableToFile_overwriteExistingFile() throws Exception {
        writeBlankTempFile();
        TimeTable timeTableToWrite = getValidTimeTable();
        IcsUtil.getInstance().saveTimeTableToFile(timeTableToWrite, TEMP_FILE);

        Optional<TimeTable> optionalTimeTableWritten = IcsUtil.getInstance().readTimeTableFromFile(TEMP_FILE);
        TimeTable timeTableRead = optionalTimeTableWritten.get();

        assertEquals(timeTableRead, timeTableToWrite);

        writeBlankTempFile(); //cleanup
    }

    //saving and reading tests
    @Test
    public void saveTimeTableToFileAndReadTimeTableFromFile_validFile_validResult() throws Exception {
        writeBlankTempFile(); //reset the temp file to blank slate.

        TimeTable timeTableToWrite = getValidTimeTable();
        IcsUtil.getInstance().saveTimeTableToFile(timeTableToWrite, TEMP_FILE);

        Optional<TimeTable> optionalTimeTableWritten = IcsUtil.getInstance().readTimeTableFromFile(TEMP_FILE);
        TimeTable timeTableRead = optionalTimeTableWritten.get();

        assertEquals(timeTableRead, timeTableToWrite);

        writeBlankTempFile(); //cleanup
    }

    /**
     * Util function
     */
    private TimeTable getValidTimeTable(){
        TimeTable timeTable = new TimeTable();
        timeTable.addTimeSlot(VALID_TIMESLOT);
        return timeTable;
    }

    /**
     * Util function
     */
    private void writeBlankTempFile(){
        try {
            FileUtil.writeToFile(TEMP_FILE, "");
        } catch (IOException e) {
            //Is it ironic if the test throws an exception?
        }
    }
}
