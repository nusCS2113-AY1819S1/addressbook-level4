package seedu.address.logic.commands;

import org.junit.*;
import seedu.address.commons.core.*;
import seedu.address.commons.util.*;
import seedu.address.logic.*;
import seedu.address.logic.commands.exceptions.*;
import seedu.address.model.*;

import java.io.File;
import java.nio.file.*;
import java.util.logging.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class BackupCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "BackupCommandTest");


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
