//@@author QzSG
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.OnlineRestoreEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.storage.OnlineStorage;

/**
 * Lists all persons in the address book to the user.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restore student planner data from location specified "
            + "(restores from default backup data path if not provided)\n"
            + "Parameters: [github authToken] OR [PATH] (must be a writable path)\n"
            + "Example: " + COMMAND_WORD + " data\\addressbook.bak OR\n"
            + "Example: " + COMMAND_WORD + " github my_personal_access_token";

    public static final String MESSAGE_SUCCESS = "Restoring Backup from %s";

    private Optional<Path> backupPath;
    private boolean isLocal = true;
    private OnlineStorage.OnlineStorageType target;
    private Optional<String> authToken;

    /**
     * Creates a RestoreCommand to backup data to storage
     */
    public RestoreCommand(Optional<Path> backupPath, boolean isLocal,
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
            model.restoreAddressBookLocal(retrievePath(model));
            return new CommandResult(String.format(MESSAGE_SUCCESS, retrievePath(model).toString()));
        } else {
            onlineRestoreTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, "GitHub Gists"));
        }

    }

    private Path retrievePath(Model model) {
        return backupPath.orElse(model.getUserPrefs().getAddressBookBackupFilePath());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestoreCommand // instanceof handles nulls
                && backupPath.equals(((RestoreCommand) other).backupPath));
    }

    /**
     * Raises event to start online restore
     */
    private void onlineRestoreTask() {
        EventsCenter.getInstance().post(new OnlineRestoreEvent(target, "53b262c0c41a18747dd3978941901057", authToken));
    }
}
