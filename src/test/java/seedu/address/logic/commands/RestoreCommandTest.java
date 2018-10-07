//@@author Limminghong
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.parser.RestoreCommandParser.ARGUMENT_SNAPSHOTS;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.backup.BackupList;


class RestoreCommandTest {
    private static final Logger logger = Logger.getLogger(ImportCommand.class.getName());

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_snapshots_success() throws Exception {
        BackupList backupList = ParserUtil.parseBackup("snapshots");
        try {
            CommandResult result = new RestoreCommand(backupList).execute(model, commandHistory);
            assertEquals(RestoreCommand.MESSAGE_BACKUP_LIST, result.feedbackToUser);
        } catch (CommandException ce) {
            logger.severe(ce.getMessage());
        }
    }

    @Test
    public void execute_index_success() throws Exception {
        Index index = ParserUtil.parseIndex("1");
        try {
            CommandResult result = new RestoreCommand(index).execute(model, commandHistory);
            assertEquals(RestoreCommand.MESSAGE_INDEX_SUCCESS, result.feedbackToUser);
        } catch (CommandException ce) {
            logger.severe(ce.getMessage());
        }
    }

    @Test
    public void execute_success() {
        try {
            CommandResult result = new RestoreCommand().execute(model, commandHistory);
            assertEquals(RestoreCommand.MESSAGE_USAGE, result.feedbackToUser);
        } catch (CommandException ce) {
            logger.severe(ce.getMessage());
        }
    }
}
