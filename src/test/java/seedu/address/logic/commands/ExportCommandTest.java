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
import seedu.address.testutil.TypicalTimeSlots;

/**
 * A system test that uses a model stub.
 */
public class ExportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "ExportCommandTest");
    private static final Path TEMP_FILE = TEST_DATA_FOLDER.resolve("temp.ics");

    private static final TimeTable VALID_TIMETABLE = TypicalTimeSlots.getTypicalTimeTable();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ExportCommand(null);
    }

    @Test
    public void execute_emptyTimeTable_failure() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        //model has an empty TimeTable. We try to export it.
        Command command = new ExportCommand(TEMP_FILE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(ExportCommand.MESSAGE_EMPTY));

        command.execute(actualModelStub, commandHistory);
    }

    /**
     * tests if you can export a typical timetable. (without testing the data integrity of the exported timetable)
     */
    @Test
    public void execute_validFilePath_exportSuccessful() throws Exception {
        ModelStubImportExportCommand actualModelStub = new ModelStubImportExportCommand();
        actualModelStub.updateTimeTable(VALID_TIMETABLE);

        Command command = new ExportCommand(TEMP_FILE);
        CommandResult commandResult = command.execute(actualModelStub, commandHistory);

        String expectedMessage = String.format(MESSAGE_EXPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * tests if you can export a typical timetable.
     * then attempts to import back the timetable (to a fresh ModelStub that has no TimeTable)
     * Tests if the imported timetable data is same as the exported timetable's data
     */
    @Test
    public void execute_exportAndThenImport_successful() throws Exception {
        //attempt export
        ModelStubImportExportCommand exportModelStub = new ModelStubImportExportCommand();
        exportModelStub.updateTimeTable(VALID_TIMETABLE);

        Command command = new ExportCommand(TEMP_FILE);
        CommandResult commandResult = command.execute(exportModelStub, commandHistory);

        String expectedMessageExport = String.format(MESSAGE_EXPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessageExport, commandResult.feedbackToUser);
        //export is successful

        //attempt import
        ModelStubImportExportCommand importModelStub = new ModelStubImportExportCommand();
        Command importCommand = new ImportCommand(TEMP_FILE);
        commandResult = importCommand.execute(importModelStub, commandHistory);

        assertEquals(importModelStub.getTimeTable(), exportModelStub.getTimeTable());
        String expectedMessageImport = String.format(MESSAGE_IMPORT_SUCCESS, TEMP_FILE);
        assertEquals(expectedMessageImport, commandResult.feedbackToUser);
        //import is successful
    }
}


