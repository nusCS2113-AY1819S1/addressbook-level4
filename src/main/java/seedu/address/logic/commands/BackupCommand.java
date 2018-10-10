package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import javafx.concurrent.Task;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
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
    private OnlineStorage.OnlineStorageType target;
    private Optional<String> authToken;

    /**
     * Creates a BackupCommand to backup data to storage
     */
    public BackupCommand(Optional<Path> backupPath, boolean isLocal,
                         Optional<OnlineStorage.OnlineStorageType> target, Optional<String> authToken) {
        this.backupPath = backupPath;
        this.isLocal = isLocal;
        this.target = target.orElse(OnlineStorage.OnlineStorageType.GITHUB);
        this.authToken = authToken;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (isLocal) {
            model.backupAddressBook(retrievePath(model));
            return new CommandResult(String.format(MESSAGE_SUCCESS, retrievePath(model).toString()));
        } else {
            new Thread(onlineBackupTask(model.getAddressBook())).start();
            return new CommandResult(String.format(MESSAGE_SUCCESS, "GitHub Gists"));
        }

    }

    private Path retrievePath(Model model) {
        return backupPath.orElse(model.getUserPrefs().getAddressBookBackupFilePath());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BackupCommand // instanceof handles nulls
                && backupPath.equals(((BackupCommand) other).backupPath));
    }

    /**
     * Background task to prevent main ui thread from freezing during online backup
     * @param addressBook
     * @return Task that can be started to run online backup on non ui thread
     */
    private Task onlineBackupTask(ReadOnlyAddressBook addressBook) {
        Task task = new Task<Void>() {
            @Override public Void call() {
                EventsCenter.getInstance().post(new OnlineBackupEvent(target, addressBook,
                        "AddressBook.bak", authToken));
                return null;
            }
        };
        return task;
    }
}
