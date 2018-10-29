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
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.OnlineStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.storage.XmlExpenseBookStorage;

//@@author QzSG

/**
 * Contains integration tests (interaction with the Model) and unit tests for BackupCommand.
 */
public class RestoreCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model;
    private Model noBackupModel;

    @Before
    public void setUp() throws Exception {
        Path tempAddressBookBackupFilePath = testFolder.getRoot().toPath().resolve("AddressBook.bak");
        Path tempExpenseBookBackupFilePath = testFolder.getRoot().toPath().resolve("ExpenseBook.bak");

        Path tempAddressBookFilePath = testFolder.getRoot().toPath().resolve("AddressBook.xml");
        Path tempExpenseBookFilePath = testFolder.getRoot().toPath().resolve("ExpenseBook.xml");

        ExpenseBook expenseBook = new ExpenseBook();
        UserPrefs userPrefs = new UserPrefs();

        userPrefs.setAddressBookBackupFilePath(tempAddressBookBackupFilePath);
        userPrefs.setExpenseBookBackupFilePath(tempExpenseBookBackupFilePath);
        userPrefs.setAddressBookFilePath(tempAddressBookFilePath);
        userPrefs.setExpenseBookFilePath(tempExpenseBookFilePath);

        UserPrefs noBackupUserPrefs = new UserPrefs();

        userPrefs.setAddressBookGistId("c877006f34937fa5133b9619e2d7be1b");
        userPrefs.setExpenseBookGistId("6e6d4388b672da5a29c951630f4610db");

        System.out.println(userPrefs.getAddressBookBackupFilePath());
        model = new ModelManager(getTypicalAddressBook(), expenseBook, userPrefs);
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(tempAddressBookBackupFilePath);
        xmlAddressBookStorage.saveAddressBook(model.getAddressBook());
        XmlExpenseBookStorage xmlExpenseBookStorage = new XmlExpenseBookStorage(tempExpenseBookBackupFilePath);
        xmlExpenseBookStorage.saveExpenseBook(model.getExpenseBook());
        noBackupModel = new ModelManager(getTypicalAddressBook(), expenseBook, noBackupUserPrefs);
    }

    @Test
    public void execute_malformedLocalBackupCommandInit_tokenExistsThrows() {
        thrown.expect(AssertionError.class);
        new RestoreCommand(
                Optional.ofNullable(model.getUserPrefs().getAddressBookBackupFilePath()), true,
                Optional.empty(), Optional.ofNullable("AUTH_TOKEN"));
    }

    @Test
    public void execute_malformedOnlineBackupCommandInit_tokenNotExistsThrows() {
        thrown.expect(AssertionError.class);
        new RestoreCommand(
                Optional.empty(), false,
                Optional.empty(), Optional.empty());
    }

    @Test
    public void execute_localRestoreSuccess() {
        RestoreCommand command = new RestoreCommand(Optional.empty(), true, Optional.empty(), Optional.empty());
        CommandResult result = command.execute(model, new CommandHistory());
        String expectedPath = model.getUserPrefs().getAddressBookBackupFilePath().getParent().toString();
        assertEquals(String.format(RestoreCommand.MESSAGE_SUCCESS, expectedPath), result.feedbackToUser);
    }

    @Test
    public void execute_onlineRestore_noPreviousBackupInitFails() {
        RestoreCommand command = new RestoreCommand(Optional.empty(), false,
                Optional.ofNullable(OnlineStorage.Type.GITHUB), Optional.ofNullable("INVALID_TOKEN"));
        CommandResult result = command.execute(noBackupModel, new CommandHistory());
        assertEquals(String.format(RestoreCommand.MESSAGE_FAILURE,
                RestoreCommand.MESSAGE_FAILURE_SAMPLE), result.feedbackToUser);
    }

    @Test
    public void execute_onlineRestoreInitSuccess() {
        RestoreCommand command = new RestoreCommand(Optional.empty(), false,
                Optional.ofNullable(OnlineStorage.Type.GITHUB),
                Optional.ofNullable("INVALID_TOKEN"));
        CommandResult result = command.execute(model, new CommandHistory());
        assertEquals(String.format(RestoreCommand.MESSAGE_SUCCESS, "GitHub Gists"), result.feedbackToUser);
    }
}
