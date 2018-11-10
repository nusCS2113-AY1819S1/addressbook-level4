package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private Model model;
    private Model expectedModel;
    private Path importFilePath;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        importFilePath = Paths.get("src", "test", "data", "sandbox", "testImportCommand.xml");
    }

    @Test
    public void execute() throws IOException {
        model.exportFilteredAddressBook(importFilePath);

        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, importFilePath);
        ImportCommand expectedCommand = new ImportCommand(importFilePath);

        assertCommandSuccess(expectedCommand, model, new CommandHistory(), expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        ImportCommand standardCommand = new ImportCommand(importFilePath);
        final Path differentFilePath = Paths.get("src", "test", "data", "testImportCommandDifferent.xml");

        // same value -> returns true
        ImportCommand commandWithSameArgument = new ImportCommand(importFilePath);
        assertTrue(standardCommand.equals(commandWithSameArgument));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null value -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different filename -> returns false
        assertFalse(standardCommand.equals(new ImportCommand(differentFilePath)));
    }
}
