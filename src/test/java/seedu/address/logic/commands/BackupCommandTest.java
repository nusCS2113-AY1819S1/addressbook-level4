package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/**
 * Contains integration tests (interaction with the Model) and unit tests for BackupCommand.
 */
public class BackupCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        Path tempBackupFilePath = testFolder.getRoot().toPath().resolve("Temp.bak");
        EventBook eventBook = new EventBook();
        ExpenseBook expenseBook = new ExpenseBook();
        TaskBook taskBook = new TaskBook();
        UserPrefs userPrefs = new UserPrefs();

        userPrefs.setAddressBookBackupFilePath(tempBackupFilePath);
        System.out.println(userPrefs.getAddressBookBackupFilePath());
        model = new ModelManager(getTypicalAddressBook(), expenseBook, eventBook, taskBook, userPrefs);
        expectedModel = new ModelManager(getTypicalAddressBook(), expenseBook, eventBook, taskBook, userPrefs);
    }

    @Test
    public void execute_malformedLocalBackupCommandInit_tokenExistsThrows() {
        thrown.expect(AssertionError.class);
        new BackupCommand(
                Optional.ofNullable(model.getUserPrefs().getAddressBookBackupFilePath()), true,
                Optional.empty(), Optional.ofNullable("AUTH_TOKEN"));
    }

    @Test
    public void execute_malformedOnlineBackupCommandInit_tokenNotExistsThrows() {
        thrown.expect(AssertionError.class);
        new BackupCommand(
                Optional.empty(), false,
                Optional.empty(), Optional.empty());
    }

    @Test
    public void execute_localBackupSuccess() {
        BackupCommand command = new BackupCommand(
                Optional.ofNullable(model.getUserPrefs().getAddressBookBackupFilePath()), true,
                Optional.empty(), Optional.empty());
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(String.format(BackupCommand.MESSAGE_SUCCESS, "local storage"), result.feedbackToUser);
    }

    @Test
    public void execute_onlineBackupSuccess() {
        BackupCommand command = new BackupCommand(Optional.empty(), false,
                        Optional.ofNullable(OnlineStorage.Type.GITHUB),
                        Optional.ofNullable("VALID_TOKEN"));
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(String.format(BackupCommand.MESSAGE_SUCCESS, "GitHub Gists"), result.feedbackToUser);
    }
}
