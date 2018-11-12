package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HistoryCommandTest {
    private CommandHistory history = new CommandHistory();
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute() {
        try {
            assertCommandSuccess(new HistoryCommand(), model,
                    history, HistoryCommand.MESSAGE_NO_HISTORY, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        String command1 = "clear";
        history.add(command1);
        try {
            assertCommandSuccess(new HistoryCommand(), model, history,
                    String.format(HistoryCommand.MESSAGE_SUCCESS, command1), expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));
        try {
            assertCommandSuccess(new HistoryCommand(), model, history, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
}
