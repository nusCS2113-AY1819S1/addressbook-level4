package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.TimeTable;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTimeSlots;

/**
 * A system test that uses a model stub. Most are testing the result of various paths the user has entered.
 */
public class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ImportCommandTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.ics"); //totally blank
    private static final Path INVALID_FILE_PATH_TOO_LONG = TEST_DATA_FOLDER.resolve(
            "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
                    + "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
                    + "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglon.ics");
    //this string is 251 char long; enough to cause the ParseException.

    private static final Path NO_DATA_FILE = TEST_DATA_FOLDER.resolve("no_data.ics"); //no calendar data
    private static final Path NO_TT_DATA_FILE = TEST_DATA_FOLDER.resolve("no_timetable_data.ics"); //no timetable data
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.ics"); //totally missing
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("temp.ics");

    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("valid.ics"); //has same data as VALID_TIMETABLE
    private static final TimeTable VALID_TIMETABLE = TypicalTimeSlots.getTypicalTimeTable();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    //Import tests
    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute_tooLongFile_throwsCommandException() throws Exception {
        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();

        Command command = new ImportCommand(INVALID_FILE_PATH_TOO_LONG);

        thrown.expectMessage(String.format(ImportCommand.MESSAGE_IO_ERROR, INVALID_FILE_PATH_TOO_LONG));
        thrown.expect(CommandException.class);
        command.execute(actualModelStub, commandHistory);
    }

    @Test
    public void execute_missingFile_throwsCommandException() throws Exception {
        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();
        Command command = new ImportCommand(MISSING_FILE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ImportCommand.MESSAGE_IO_ERROR, MISSING_FILE));

        command.execute(actualModelStub, commandHistory);
    }

    @Test
    public void execute_noICalendarDataFile_showsMessageEmpty() throws Exception {
        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();
        Command command = new ImportCommand(NO_DATA_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_EMPTY, NO_DATA_FILE.toString());

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_noTimeTableDataFile_showsMessageEmpty() throws Exception {
        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();
        Command command = new ImportCommand(NO_TT_DATA_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_EMPTY, NO_TT_DATA_FILE.toString());
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    @Test
    public void execute_emptyFile_showsMessageEmpty() throws Exception {
        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();
        Command command = new ImportCommand(EMPTY_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);

        assertEquals(String.format(ImportCommand.MESSAGE_EMPTY, EMPTY_FILE.toString()), commandResult.feedbackToUser);
    }

    /**
     * tests if you can import a typical timetable.
     * Immediately then tests that the imported timetable is identical to the expected timetable.
     */
    @Test
    public void execute_validFilePath_importSuccessful() throws Exception {
        TimeTable expectedTimeTable = VALID_TIMETABLE;

        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();

        Command command = new ImportCommand(VALID_FILE); //VALID_FILE contains the equivalent of VALID_TIMETABLE
        CommandResult commandResult = command.execute(actualModelStub, commandHistory);

        assertEquals(expectedTimeTable, actualModelStub.getTimeTable());
        String expectedMessage = String.format(MESSAGE_IMPORT_SUCCESS, VALID_FILE);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }


    /**
     * tests if you can export a typical timetable and then immediate import this timetable
     * Immediately then tests that the data is still the same.
     */
    @Test
    public void execute_exportAndThenImport_successful() throws Exception {

        ModelStubImportCommandTest actualModelStub = new ModelStubImportCommandTest();
        actualModelStub.updateTimeTable(VALID_TIMETABLE);

        Command exportCommand = new ExportCommand(TEMP_FILE); //export timetable to temp file.
        exportCommand.execute(actualModelStub, commandHistory);

        Command importCommand = new ImportCommand(TEMP_FILE); //import timetable from temp file.
        CommandResult commandResult = importCommand.execute(actualModelStub, commandHistory);

        assertEquals(VALID_TIMETABLE, actualModelStub.getTimeTable());
        String expectedMessage = String.format(MESSAGE_IMPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}


