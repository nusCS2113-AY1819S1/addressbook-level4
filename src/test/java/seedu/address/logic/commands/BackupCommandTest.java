package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class BackupCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "BackupCommandTest");
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookBackupFilePath(addToTestDataPathIfNotNull("AddressBook.bak"));
        model = new ModelManager(getTypicalAddressBook(), userPrefs);
        expectedModel = new ModelManager(getTypicalAddressBook(), userPrefs);
    }

    @Test
    public void execute_backupSuccess() {
        BackupCommand command = new BackupCommand();
        BackupCommand expectedCommand = new BackupCommand();
        CommandResult result = command.execute(model, new CommandHistory());
        CommandResult expectedResult = expectedCommand.execute(expectedModel, new CommandHistory());
        assertEquals(expectedResult.feedbackToUser, result.feedbackToUser);
    }

    private Path addToTestDataPathIfNotNull(String backupFileInTestDataFolder) {
        return backupFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(backupFileInTestDataFolder)
                : null;
    }

}
