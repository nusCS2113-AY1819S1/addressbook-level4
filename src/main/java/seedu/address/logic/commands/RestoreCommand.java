//@@author Limminghong
package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.backup.BackupList;

import java.util.List;

/**
 * Restores the address book to a snapshot of choice.
 */
public class RestoreCommand extends Command {
    public static final String COMMAND_WORD = CliSyntax.COMMAND_RESTORE;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the address book to a snapshot of choice.\n"
            + "Parameters:" + " snapshots"
            + " or" + " DD/MM/YYYY" + " time";
    public static final String MESSAGE_INDEX_SUCCESS = "Parser for index works :)";

    public static final String NUMBER_OF_SNAPSHOTS_PLURAL = " snapshots listed!";
    public static final String NUMBER_OF_SNAPSHOTS = " snapshot listed!";

    public static String MESSAGE_BACKUP_LIST;

    private int flag;
    private List<String> fileNames;

    public RestoreCommand() {
        this.flag = 0;
    }

    public RestoreCommand(BackupList backupList) {
        fileNames = backupList.getFileNames();
        if(fileNames.size() == 1) {
            MESSAGE_BACKUP_LIST = Integer.toString(fileNames.size()) + NUMBER_OF_SNAPSHOTS +"\n";
        } else {
            MESSAGE_BACKUP_LIST = Integer.toString(fileNames.size()) + NUMBER_OF_SNAPSHOTS_PLURAL+ "\n";
        }
        for (int i = 1; i <= fileNames.size(); i++) {
            MESSAGE_BACKUP_LIST += Integer.toString(i) + ". " + fileNames.get(i - 1) + "\n";
        }
        this.flag = 1;
    }

    public RestoreCommand(Index index) {
        this.flag = 2;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        FileEncryptor fe = new FileEncryptor("data/addressbook.xml");

        if (fe.isLocked()) {
            throw new CommandException(FileEncryptor.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        if (flag == 1) {
            return new CommandResult(MESSAGE_BACKUP_LIST);
        } else if (flag == 2) {
            return new CommandResult(MESSAGE_INDEX_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_USAGE);
        }
    }
}
