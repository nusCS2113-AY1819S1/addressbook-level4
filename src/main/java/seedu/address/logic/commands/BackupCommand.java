package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.LocalBackupEvent;
import seedu.address.commons.events.storage.OnlineBackupEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/**
 * Lists all persons in the address book to the user.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Backups student planner data to location specified (backups to default data path if not provided)\n"
            + "Parameters: [github authToken]\n"
            + "Example: " + COMMAND_WORD + "\n"
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
        if (isLocal && authToken.isPresent()) {
            throw new AssertionError("This should never happen. authToken should not exist if isLocal is true.");
        }
        if (!isLocal && !authToken.isPresent()) {
            throw new AssertionError("This should never happen. authToken should always exist if isLocal is false.");
        }
        this.backupPath = backupPath;
        this.isLocal = isLocal;
        this.target = target.orElse(OnlineStorage.Type.GITHUB);
        this.authToken = authToken;

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        if (isLocal) {

            localBackupCommand(model);
            return new CommandResult(String.format(MESSAGE_SUCCESS, "local storage"));

        } else {

            onlineBackupCommand(model);
            return new CommandResult(String.format(MESSAGE_SUCCESS, "GitHub Gists"));

        }

    }

    /*
    @SuppressWarnings("unused")
    private Path retrievePath(Model model) {
        return backupPath.orElse(model.getUserPrefs().getAddressBookBackupFilePath());
    }

    @SuppressWarnings("unused")
    private Path retrieveAddressBookPath(Model model) {
        return model.getUserPrefs().getAddressBookBackupFilePath();
    }

    @SuppressWarnings("unused")
    private Path retrieveExpenseBookPath(Model model) {
        return model.getUserPrefs().getExpenseBookBackupFilePath();
    }*/

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BackupCommand // instanceof handles nulls
                && backupPath.equals(((BackupCommand) other).backupPath));
    }

    /**
     * Raises event to indicate new online backup command
     * @param model Memory model
     */
    private void onlineBackupCommand(Model model) {
        EventsCenter.getInstance().post(
                new OnlineBackupEvent(target, model.getAddressBook(), model.getEventBook(),
                        model.getExpenseBook(), model.getTaskBook(), authToken));
    }

    /**
     * Raises event to indicate new online backup command
     * @param model Memory model
     */
    private void localBackupCommand(Model model) {
        EventsCenter.getInstance().post(new LocalBackupEvent(
                model.getAddressBook(), model.getUserPrefs().getAddressBookBackupFilePath(),
                model.getEventBook(), model.getUserPrefs().getEventBookBackupFilePath(),
                model.getExpenseBook(), model.getUserPrefs().getExpenseBookBackupFilePath(),
                model.getTaskBook(), model.getUserPrefs().getTaskBookBackupFilePath()));
    }
}
