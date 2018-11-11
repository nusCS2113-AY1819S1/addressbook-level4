package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.AddressBookBuilder;

//@@author jitwei98
/**
 * Contains integration tests (interaction with the Model) and unit tests for ImportCommand.
 */
public class ImportCommandTest {

    private static final int PERSONS_ADDED = 2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private Path importFilePath;

    @Before
    public void setUp() throws IOException, IllegalValueException {
        model = new ModelManager(new AddressBookBuilder().build(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build(),
                new UserPrefs());
        importFilePath = Paths.get("src", "test", "data", "sandbox", "testImportCommand.xml");
        expectedModel.exportFilteredAddressBook(importFilePath);
    }

    @Test
    public void constructor_nullImportFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null);
    }

    @Test
    public void execute() throws CommandException {
        CommandResult commandResult = new ImportCommand(importFilePath).execute(model, new CommandHistory());
        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, PERSONS_ADDED);

        ReadOnlyAddressBook addressBook = model.getAddressBook();
        ReadOnlyAddressBook expectedAddressBook = expectedModel.getAddressBook();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        // Cannot compare model since todos are not imported
        assertEquals(expectedAddressBook.getPersonList(), addressBook.getPersonList());
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
