package seedu.recruit.logic.commands.emailcommand;

import static org.junit.Assert.assertEquals;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

public class EmailSendCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();
    private ModelManager model = new ModelManager();

    @Test
    public void execute_sendCommand() {
        try {
            new EmailSendCommand().execute(model, commandHistory, userPrefs);
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
}
