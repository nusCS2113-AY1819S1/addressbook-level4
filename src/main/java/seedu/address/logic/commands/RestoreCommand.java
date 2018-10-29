//@@author QzSG
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.LocalRestoreEvent;
import seedu.address.commons.events.storage.OnlineRestoreEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.storage.OnlineStorage;

/**
 * Lists all persons in the address book to the user.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restore student planner data from location specified "
            + "(restores from default backup data path if no parameters provided)\n"
            + "Parameters: [github authToken]\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " github my_personal_access_token";

    public static final String MESSAGE_SUCCESS = "Restoring Backup from %s";
    public static final String MESSAGE_FAILURE = "Please perform an online backup using %s first or set relevant"
             + " settings in user prefs";
    public static final String MESSAGE_FAILURE_SAMPLE = ": backup github [personal_access_token]";
    public static final String MESSAGE_INVALID = "Invalid online service provided";
    private Optional<Path> backupPath;
    private boolean isLocal;
    private OnlineStorage.Type target;
    private Optional<String> authToken;

    /**
     * Creates a RestoreCommand to backup data to storage
     */
    public RestoreCommand(Optional<Path> backupPath, boolean isLocal,
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
            return localRestoreCommand(model);
        } else {
            return onlineRestoreCommand(model);
        }

    }

    /**
     * Raises event to indicate new local restore command
     * @param model
     * @return
     */
    private CommandResult localRestoreCommand(Model model) {
        EventsCenter.getInstance().post(new LocalRestoreEvent(
                retrieveAddressBookPath(model), retrieveExpenseBookPath(model)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, retrievePath(model).getParent().toString()));
    }

    /**
     * Raises event to indicate new online restore command
     * @param model
     * @return
     */
    private CommandResult onlineRestoreCommand(Model model) {
        if (target == OnlineStorage.Type.GITHUB) {
            String gistId = model.getUserPrefs().getAddressBookGistId();
            if (gistId == null) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_FAILURE_SAMPLE));
            }
            EventsCenter.getInstance().post(new OnlineRestoreEvent(target, UserPrefs.TargetBook.AddressBook,
                    model.getUserPrefs().getAddressBookGistId(), authToken));
            EventsCenter.getInstance().post(new OnlineRestoreEvent(target, UserPrefs.TargetBook.ExpenseBook,
                    model.getUserPrefs().getExpenseBookGistId(), authToken));
            return new CommandResult(String.format(MESSAGE_SUCCESS, "GitHub Gists"));
        } else {
            return new CommandResult(MESSAGE_INVALID);
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
                || (other instanceof RestoreCommand // instanceof handles nulls
                && backupPath.equals(((RestoreCommand) other).backupPath));
    }
}
