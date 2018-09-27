package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.backup.BackupList;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

class RestoreCommandTest {
    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_snapshots_success() {
        BackupList backupList = new BackupList("Success");
        CommandResult result = new RestoreCommand(backupList).execute(model, commandHistory);
        assertEquals(RestoreCommand.MESSAGE_BACKUP_LIST_SUCCESS, result.feedbackToUser);
    }

    @Test
    public void execute_index_success() throws Exception{
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