package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Filetype;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ExportCommand.
 */
public class ExportCommandTest {

    private Model model;
    private Model expectedModel;
    private Path exportFilePath;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        exportFilePath = Paths.get("src", "test", "data", "sandbox", "testExportCommand.xml");
    }

    @Test
    public void execute() {
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, exportFilePath);
        ExportCommand expectedCommand = new ExportCommand(exportFilePath);

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ExportCommand standardCommand = new ExportCommand(exportFilePath);
        final Filetype filetypeCsv = new Filetype("csv");
        final Path differentFilePath = Paths.get("src", "test", "data", "testExportCommandDifferent.xml");

        // same value -> returns true
        ExportCommand commandWithSameArgument = new ExportCommand(exportFilePath);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ExportAllCommand(filetypeCsv)));

        // different filename -> returns false
        assertFalse(standardCommand.equals(new ExportCommand(differentFilePath)));
    }
}
