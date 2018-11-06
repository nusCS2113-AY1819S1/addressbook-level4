package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.ExportCommand.MESSAGE_EXPORT_SUCCESS;
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
 * A system test that uses a model stub.
 */
public class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ImportCommandTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.ics"); //totally blank file
    private static final Path NO_DATA_FILE = TEST_DATA_FOLDER.resolve("no_data.ics"); //no calendar data
    private static final Path NO_TT_DATA_FILE = TEST_DATA_FOLDER.resolve("no_timetable_data.ics"); //no timetable data
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.ics"); //totally missing
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("temp.ics");

    private static final Path TYPICAL_FILE = TEST_DATA_FOLDER.resolve("typical.ics"); //has same data as TYPICAL_TIMETABLE
    private static final TimeTable TYPICAL_TIMETABLE = TypicalTimeSlots.getTypicalTimeTable();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    //Trying to import non-existent file; tell user it is missing.
    @Test
    public void execute_missingFile_throwsCommandException() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        Command command = new ImportCommand(MISSING_FILE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ImportCommand.MESSAGE_IO_ERROR, MISSING_FILE));

        command.execute(actualModelStub, commandHistory);
    }

    //Trying to import file with no iCalendar data; tell user it is empty.
    @Test
    public void execute_noICalendarDataFile_showsMessageEmpty() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        Command command = new ImportCommand(NO_DATA_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_EMPTY, NO_DATA_FILE.toString());

        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    //Trying to import file that has iCalendar data, but has no Timetable data; tell user it is empty.
    @Test
    public void execute_noTimeTableDataFile_showsMessageEmpty() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        Command command = new ImportCommand(NO_TT_DATA_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);
        String expectedMessage = String.format(ImportCommand.MESSAGE_EMPTY, NO_TT_DATA_FILE.toString());
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    //Trying to import completely empty file; tell user it is empty.
    @Test
    public void execute_emptyFile_showsMessageEmpty() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        Command command = new ImportCommand(EMPTY_FILE);

        CommandResult commandResult = command.execute(actualModelStub, commandHistory);

        assertEquals(String.format(ImportCommand.MESSAGE_EMPTY, EMPTY_FILE.toString()), commandResult.feedbackToUser);
    }

    /**
     * Tests if an imported timetable is identical to the expected timetable.
     */
    @Test
    public void execute_validFilePath_successful() throws Exception {
        //TYPICAL_FILE contains the equivalent of TYPICAL_TIMETABLE
        TimeTable expectedTimeTable = TYPICAL_TIMETABLE;

        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();

        Command command = new ImportCommand(TYPICAL_FILE);
        CommandResult commandResult = command.execute(actualModelStub, commandHistory);

        assertEquals(expectedTimeTable, actualModelStub.getTimeTable());
        String expectedMessage = String.format(MESSAGE_IMPORT_SUCCESS, TYPICAL_FILE);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * tests if you can export a typical timetable and then immediately import this timetable
     * Immediately then tests that the data is still the same.
     */
    @Test
    public void execute_exportAndThenImport_successful() throws Exception {

        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        actualModelStub.updateTimeTable(TYPICAL_TIMETABLE);

        Command exportCommand = new ExportCommand(TEMP_FILE); //export timetable to temp file.
        CommandResult commandResult = exportCommand.execute(actualModelStub, commandHistory);

        //check export was successful
        String expectedMessageExport = String.format(MESSAGE_EXPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessageExport, commandResult.feedbackToUser);

        Command importCommand = new ImportCommand(TEMP_FILE); //import timetable from temp file.
        commandResult = importCommand.execute(actualModelStub, commandHistory);

        //check import was successful, and timetable is the same
        assertEquals(TYPICAL_TIMETABLE, actualModelStub.getTimeTable());
        String expectedMessageImport = String.format(MESSAGE_IMPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessageImport, commandResult.feedbackToUser);
    }
}


