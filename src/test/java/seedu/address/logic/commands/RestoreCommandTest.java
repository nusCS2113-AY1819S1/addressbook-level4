package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.backup.BackupList;

class RestoreCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_snapshots_success() {
        BackupList backupList = new BackupList("Success");
        CommandResult result = new RestoreCommand(backupList).execute(model, commandHistory);
        assertEquals(RestoreCommand.MESSAGE_BACKUP_LIST_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_index_success() throws Exception {
        Index index = ParserUtil.parseIndex("1");
        CommandResult result = new RestoreCommand(index).execute(model, commandHistory);
        assertEquals(RestoreCommand.MESSAGE_INDEX_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_success() {
        CommandResult result = new RestoreCommand().execute(model, commandHistory);
        assertEquals(RestoreCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
