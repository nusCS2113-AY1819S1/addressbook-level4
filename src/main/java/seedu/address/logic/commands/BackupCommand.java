package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author QzSG
/**
 * Lists all persons in the address book to the user.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Backups student planner data to location specified (backups to default data path if not provided)\n"
            + "Parameters: [PATH] (must be a writable path)\n"
            + "Example: " + COMMAND_WORD + " data\\addressbook.bak";

    public static final String MESSAGE_SUCCESS = "Initiating Backup to %s";

    private Optional<Path> backupPath;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public BackupCommand(Optional<Path> backupPath) {
        this.backupPath = backupPath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.backupAddressBook(retrievePath(model));
        System.out.println(retrievePath(model));
        return new CommandResult(String.format(MESSAGE_SUCCESS, retrievePath(model).toString()));
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
}
