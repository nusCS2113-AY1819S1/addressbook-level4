package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author QzSG
/**
 * Contains integration tests (interaction with the Model) and unit tests for BackupCommand.
 */
public class BackupCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        Path tempBackupFilePath = testFolder.getRoot().toPath().resolve("Temp.bak");
        UserPrefs userPrefs = new UserPrefs();

        userPrefs.setAddressBookBackupFilePath(tempBackupFilePath);
        System.out.println(userPrefs.getAddressBookBackupFilePath());
        model = new ModelManager(getTypicalAddressBook(), userPrefs);
        expectedModel = new ModelManager(getTypicalAddressBook(), userPrefs);
    }

    @Test
    public void execute_backupSuccess() {
        BackupCommand command = new BackupCommand(
                Optional.ofNullable(model.getUserPrefs().getAddressBookBackupFilePath()));
        BackupCommand expectedCommand = new BackupCommand(
                Optional.ofNullable(model.getUserPrefs().getAddressBookBackupFilePath()));
        CommandResult result = command.execute(model, new CommandHistory());
        CommandResult expectedResult = expectedCommand.execute(expectedModel, new CommandHistory());
        assertEquals(expectedResult.feedbackToUser, result.feedbackToUser);
    }



}
