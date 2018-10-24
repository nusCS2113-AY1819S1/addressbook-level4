package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/**
 * Lists all persons in the address book to the user.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Backups student planner data to location specified (backups to default data path if not provided)\n"
            + "Parameters: [github authToken] OR [PATH] (must be a writable path)\n"
            + "Example: " + COMMAND_WORD + " data\\addressbook.bak OR\n"
            + "Example: " + COMMAND_WORD + " github my_personal_access_token";

    public static final String MESSAGE_SUCCESS = "Initiating Backup to %s";

    private Optional<Path> backupPath;
    private boolean isLocal = true;
    private OnlineStorage.Type target;
    private Optional<String> authToken;

    /**
     * Creates a BackupCommand to backup data to storage
     */
    public BackupCommand(Optional<Path> backupPath, boolean isLocal,
                         Optional<OnlineStorage.Type> target, Optional<String> authToken) {
        this.backupPath = backupPath;
        this.isLocal = isLocal;
        this.target = target.orElse(OnlineStorage.Type.GITHUB);
        this.authToken = authToken;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (isLocal) {
            //model.backupAddressBookLocal(retrievePath(model));
            model.backupAddressBookLocal(retrieveAddressBookPath(model));
            model.backupExpenseBookLocal(retrieveExpenseBookPath(model));
            return new CommandResult(String.format(MESSAGE_SUCCESS, retrievePath(model).toString()));
        } else {
            onlineBackupTask(model.getAddressBook(), model.getExpenseBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS, "GitHub Gists"));
        }

    }

    private Path retrievePath(Model model) {
        return backupPath.orElse(model.getUserPrefs().getAddressBookBackupFilePath());
    }

    private Path retrieveAddressBookPath(Model model) {
        return model.getUserPrefs().getAddressBookBackupFilePath();
    }

    private Path retrieveExpenseBookPath(Model model) {
        return model.getUserPrefs().getExpenseBookBackupFilePath();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BackupCommand // instanceof handles nulls
                && backupPath.equals(((BackupCommand) other).backupPath));
    }

    /**
     * Raises event to start online backup
     * @param addressBook
     */
    private void onlineBackupTask(ReadOnlyAddressBook addressBook, ReadOnlyExpenseBook expenseBook) {
        EventsCenter.getInstance().post(
                new OnlineBackupEvent(target, addressBook, expenseBook, authToken));
    }
}
