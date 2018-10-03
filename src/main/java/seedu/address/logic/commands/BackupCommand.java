package seedu.address.logic.commands;

import seedu.address.commons.core.*;
import seedu.address.commons.events.model.*;
import seedu.address.logic.*;
import seedu.address.model.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_SUCCESS = "Student Planner backup successful. Backup location : %s";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.backupAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getUserPrefs().getAddressBookBackupFilePath()));
    }
}
