package seedu.recruit.logic.commands.emailcommand;

import static org.junit.Assert.assertEquals;
import static seedu.recruit.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.recruit.logic.CommandHistory;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.Model;
import seedu.recruit.model.ModelManager;
import seedu.recruit.model.UserPrefs;

class EmailContentsCommandTest {

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();
    private UserPrefs userPrefs = new UserPrefs();

    @Test
    void execute_emailContentsCommand() {
        try {
            new EmailContentsCommand().execute(model, commandHistory, userPrefs);
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
}
